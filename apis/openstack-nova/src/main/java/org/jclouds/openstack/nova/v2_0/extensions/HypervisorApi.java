package org.jclouds.openstack.nova.v2_0.extensions;



import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.EmptyFluentIterableOnNotFoundOr404;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.nova.v2_0.domain.Hypervisor;
import org.jclouds.openstack.nova.v2_0.domain.Volume;
import org.jclouds.openstack.nova.v2_0.functions.internal.ParseFlavorDetails;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SelectJson;

import com.google.common.annotations.Beta;
import com.google.common.collect.FluentIterable;

@Beta
@Extension(of = ServiceType.COMPUTE,namespace = ExtensionNamespaces.HYPERVISORS)
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
public interface HypervisorApi {
	
	@Named("hypervisor:list")
	@GET
	@Path("/os-hypervisors")
	@SelectJson("hypervisors")
	@Fallback(EmptyFluentIterableOnNotFoundOr404.class)
	FluentIterable<Hypervisor> list();
	

	
	
	@Named("hypervisor:list")
	@GET
	@Path("/os-hypervisors/detail")
	@SelectJson("hypervisors")
	@Fallback(EmptyFluentIterableOnNotFoundOr404.class)
	FluentIterable<Hypervisor> listInDetail();
}
