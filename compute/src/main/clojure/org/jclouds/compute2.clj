;
;
; Copyright (C) 2011 Cloud Conscious, LLC. <info@cloudconscious.com>
;
; ====================================================================
; Licensed under the Apache License, Version 2.0 (the "License");
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
;
; http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.
; ====================================================================
;

(ns org.jclouds.compute2
  "A clojure binding to the jclouds ComputeService.

  Current supported providers are:
   [aws-ec2, eucualyptus-partnercloud-ec2, elastichosts-lon-b,
    cloudservers-uk, cloudservers-us, byon, cloudsigma-zrh, stub,
    trmk-ecloud, trmk-vcloudexpress, vcloud, bluelock, eucalyptus,
    slicehost, elastichosts-lon-p, elastichosts-sat-p, elastichosts,
    openhosting-east1, serverlove-z1-man, skalicloud-sdg-my, deltacloud]

  Here's an example of getting some compute configuration from rackspace:"
  (:use org.jclouds.core
    (clojure.contrib logging core))
  (:require
   [clojure.contrib.condition :as condition])
  (:import java.io.File
    java.util.Properties
    [org.jclouds.domain Location]
    [org.jclouds.compute
     ComputeService ComputeServiceContext ComputeServiceContextFactory]
    [org.jclouds.compute.domain
     Template TemplateBuilder ComputeMetadata NodeMetadata Hardware
     OsFamily Image]
    [org.jclouds.compute.options TemplateOptions]
    [org.jclouds.compute.predicates
     NodePredicates]
    [com.google.common.collect ImmutableSet])
  )

(try
  (use '[clojure.contrib.reflect :only [get-field]])
  (catch Exception e
    (use '[clojure.contrib.java-utils
           :only [wall-hack-field]
           :rename {wall-hack-field get-field}])))

(defn compute-service
  "Create a logged in context."
  ([#^String provider #^String provider-identity #^String provider-credential
    & options]
    (let [module-keys (set (keys module-lookup))
          ext-modules (filter #(module-keys %) options)
          opts (apply hash-map (filter #(not (module-keys %)) options))]
      (.. (ComputeServiceContextFactory.)
        (createContext
          provider provider-identity provider-credential
          (apply modules (concat ext-modules (opts :extensions)))
          (reduce #(do (.put %1 (name (first %2)) (second %2)) %1)
            (Properties.) (dissoc opts :extensions)))
        (getComputeService))))
  ([#^ComputeServiceContext compute-context]
    (.getComputeService compute-context)))

(defn compute-context
  "Returns a compute context from a compute service."
  [compute]
  (.getContext compute))

(defn compute-service?
  [object]
  (instance? ComputeService object))

(defn compute-context?
  [object]
  (instance? ComputeServiceContext object))

(defn locations
  "Retrieve the available compute locations for the compute context."
  ([#^ComputeService compute]
    (seq (.listAssignableLocations compute))))

(defn nodes
  "Retrieve the existing nodes for the compute context."
  ([#^ComputeService compute]
    (seq (.listNodes compute))))

(defn nodes-with-details
  "Retrieve the existing nodes for the compute context."
  ([#^ComputeService compute]
    (seq (.listNodesDetailsMatching compute (NodePredicates/all)))))

(defn nodes-in-group
  "list details of all the nodes in the given group."
  ([#^String group #^ComputeService compute]
    (filter #(= (.getTag %) group) (nodes-with-details compute))))

(defn images
  "Retrieve the available images for the compute context."
  ([#^ComputeService compute]
    (seq (.listImages compute))))

(defn hardware-profiles
  "Retrieve the available node hardware profiles for the compute context."
  ([#^ComputeService compute]
    (seq (.listHardwareProfiles compute))))

(defn default-template
  ([#^ComputeService compute]
    (.. compute (templateBuilder)
      (options
        (org.jclouds.compute.options.TemplateOptions$Builder/authorizePublicKey
          (slurp (str (. System getProperty "user.home") "/.ssh/id_rsa.pub"))))
      build)))

(defn create-nodes
  "Create the specified number of nodes using the default or specified
   template.

  ;; Simplest way to add 2 small linux nodes to the group webserver is to run
  (create-nodes \"webserver\" 2 compute)

  ;; which is the same as wrapping the create-nodes command with an implicit
  ;; compute service.
  ;; Note that this will actually add another 2 nodes to the set called
  ;; \"webserver\"

  TODO: Examples
  "
  ([group count compute]
      (create-nodes
        group count (default-template compute) compute))
  ([group count template #^ComputeService compute]
    (seq
      (.createNodesInGroup compute group count template))))

(defn create-node
  "Create a node using the default or specified template.

  ;; simplest way to add a small linux node to the group webserver is to run
  (create-node \"webserver\" compute)

  ;; which is the same as wrapping the create-node command with an implicit compute
  ;; service.
  ;; Note that this will actually add another node to the set called
  ;;  \"webserver\"
  TODO: Examples
  "
  ([group compute]
    (create-nodes group 1 (default-template compute)))

  ([group compute template]
    (first (create-nodes group 1 template compute))))

(defn #^NodeMetadata node-details
  "Retrieve the node metadata, given its id."
  ([id #^ComputeService compute]
    (.getNodeMetadata compute id)))

(defn suspend-nodes-in-group
  "Reboot all the nodes in the given group."
  ([#^String group #^ComputeService compute]
    (.suspendNodesMatching compute (NodePredicates/inGroup group))))

(defn suspend-node
  "Suspend a node, given its id."
  ([id #^ComputeService compute]
    (.suspendNode compute id)))

(defn resume-nodes-in-group
  "Suspend all the nodes in the given group."
  ([#^String group #^ComputeService compute]
    (.resumeNodesMatching compute (NodePredicates/inGroup group))))

(defn resume-node
  "Resume a node, given its id."
  ([id #^ComputeService compute]
    (.resumeNode compute id)))

(defn reboot-nodes-in-group
  "Reboot all the nodes in the given group."
  ([#^String group #^ComputeService compute]
    (.rebootNodesMatching compute (NodePredicates/inGroup group))))

(defn reboot-node
  "Reboot a node, given its id."
  ([id #^ComputeService compute]
    (.rebootNode compute id)))

(defn destroy-nodes-in-group
  "Destroy all the nodes in the given group."
  ([#^String group #^ComputeService compute]
    (.destroyNodesMatching compute (NodePredicates/inGroup group))))

(defn destroy-node
  "Destroy a node, given its id."
  ([id #^ComputeService compute]
    (.destroyNode compute id)))

(defmacro state-predicate [node state]
  `(= (.getState ~node)
    (. org.jclouds.compute.domain.NodeState ~state)))

(defn pending?
  "Predicate for the node being in transition"
  [#^NodeMetadata node]
  (state-predicate node PENDING))

(defn running?
  "Predicate for the node being available for requests."
  [#^NodeMetadata node]
  (state-predicate node RUNNING))

(defn terminated?
  "Predicate for the node being halted."
  [#^NodeMetadata node]
  (state-predicate node TERMINATED))

(defn suspended?
  "Predicate for the node being suspended."
  [#^NodeMetadata node]
  (state-predicate node SUSPENDED))

(defn error-state?
  "Predicate for the node being in an error state."
  [#^NodeMetadata node]
  (state-predicate node ERROR))

(defn unrecognized-state?
  "Predicate for the node being in an unrecognized state."
  [#^NodeMetadata node]
  (state-predicate node UNRECOGNIZED))

(defn public-ips
  "Returns the node's public ips"
  [#^NodeMetadata node]
  (.getPublicAddresses node))

(defn private-ips
  "Returns the node's private ips"
  [#^NodeMetadata node]
  (.getPrivateAddresses node))

(defn group
  "Returns a the node's group"
  [#^NodeMetadata node]
  (.getGroup node))

(defn hostname
  "Returns the compute node's name"
  [#^ComputeMetadata node]
  (.getName node))

(defn location
  "Returns the compute node's location id"
  [#^ComputeMetadata node]
  (-?> node .getLocation .getId))

(defn id
  "Returns the compute node's id"
  [#^ComputeMetadata node]
  (.getId node))

(define-accessors Template image hardware location options)
(define-accessors Image version os-family os-description architecture)
(define-accessors Hardware processors ram volumes)
(define-accessors NodeMetadata "node" credentials hardware state group)

(def
  ^{:doc "TemplateBuilder functions" :private true}
  template-map
  (merge
    (make-option-map
      kw-memfn-0arg [:smallest :fastest :biggest :any])
    (make-option-map
      kw-memfn-1arg
      [:os-family :location-id :architecture :image-id :hardware-id
       :os-name-matches :os-version-matches :os-description-matches
       :os-64-bit :image-version-matches :image-name-matches
       :image-description-matches :min-cores :min-ram])))

(def
  ^{:doc "TemplateOptions functions" :private true}
  options-map
  (merge
    (make-option-map
      kw-memfn-0arg
      [:destroy-on-error :enable-monitoring :no-placement-group :no-key-pair
       :with-details])
    (make-option-map
      kw-memfn-1arg
      [:run-script :install-private-key :authorize-public-key
       ;; aws ec2 options
       :spot-price :spot-options :placement-group :subnet-id
       :block-device-mappings :unmapDeviceNamed :security-groups
       :key-pair :user-data])
    (make-option-map kw-memfn-varargs [:inbound-ports])
    (make-option-map
      kw-memfn-2arg
      [:block-on-port
       ;; aws ec2 options
       :map-ephemeral-device-to-device-name])
    {:map-ebs-snapshot-to-device-name
     (kw-memfn-apply :map-ebs-snapshot-to-device-name
       device-name snapshot-id size-in-gib delete-on-termination)
     :map-new-volume-to-device-name
     (kw-memfn-apply :map-new-volume-to-device-name
       device-name size-in-gib delete-on-termination)}))

(def
  ^{:doc "All receognised options"}
  known-template-options
  (set (mapcat keys [options-map template-map])))

(defn os-families []
  (. OsFamily values))

(def enum-map {:os-family (os-families)})

(defn translate-enum-value [kword value]
  (or (-> (filter #(= (name value) (str %)) (kword enum-map)) first)
    value))

(defn apply-option [builder option-map option value]
  (when-let [f (option-map option)]
    (f builder (translate-enum-value option value))))

(defn build-template
  "Creates a template that can be used to run nodes.

The :os-family key expects a keyword version of OsFamily,
  eg. :os-family :ubuntu.

The :smallest, :fastest, :biggest, :any, and :destroy-on-error keys expect a
boolean value.

Options correspond to TemplateBuilder methods."
  [#^ComputeService compute
   {:keys [os-family location-id architecture image-id hardware-id
           os-name-matches os-version-matches os-description-matches
           os-64-bit image-version-matches image-name-matches
           image-description-matches min-cores min-ram
           run-script install-private-key authorize-public-key
           inbound-ports smallest fastest biggest any destroy-on-error]
    :as options}]
  (let [builder (.. compute (templateBuilder))]
    (doseq [[option value] options]
      (when-not (known-template-options option)
        (condition/raise
          :type :invalid-template-builder-option
          :message (format "Invalid template builder option : %s" option)))
      ;; apply template builder options
      (try
        (apply-option builder template-map option value)
        (catch Exception e
          (condition/raise
            :type :invalid-template-builder
            :message (format
            "Problem applying template builder %s with value %s: %s"
            option (pr-str value) (.getMessage e))
            :cause e))))
    (let [template (.build builder)
          template-options (.getOptions template)]
      (doseq [[option value] options]
        ;; apply template option options
        (try
          (apply-option template-options options-map option value)
          (catch Exception e
            (condition/raise
              :type :invalid-template-option
              :message (format
              "Problem applying template option %s with value %s: %s"
              option (pr-str value) (.getMessage e))
              :cause e))))
      template)))
