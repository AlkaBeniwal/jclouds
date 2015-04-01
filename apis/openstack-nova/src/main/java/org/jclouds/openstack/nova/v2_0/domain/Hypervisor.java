package org.jclouds.openstack.nova.v2_0.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;

import javax.inject.Named;

import org.jclouds.javax.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

public class Hypervisor {

	public static Builder<?> builder() {
		return new ConcreteBuilder();
	}

	public Builder<?> toBuilder() {
		return new ConcreteBuilder().fromHypervisor(this);
	}

	public abstract static class Builder<T extends Builder<T>> {
		protected abstract T self();

		protected String id;
		protected String hypervisorHost;
		protected int currentWorkload;
		protected int diskAvailableLeast;
		protected int freeDiskGb;
		protected int freeRamMb;
		protected String hypervisorType;
		protected int hypervisorVersion;
		protected int localGb;
		protected int localGbUsed;
		protected int memoryMb;
		protected int memoryMbUsed;
		protected int runningVms;
		protected int vcpus;
		protected int vcpusUsed;

		public T id(String id) {
			this.id = id;
			return self();
		}

		public T hypervisorHost(String hypervisorHost) {
			this.hypervisorHost = hypervisorHost;
			return self();
		}

		public T currentWorkload(int currentWorkload) {
			this.currentWorkload = currentWorkload;
			return self();
		}

		public T diskAvailableLeast(int diskAvailableLeast) {
			this.diskAvailableLeast = diskAvailableLeast;
			return self();
		}

		public T freeDiskGb(int freeDiskGb) {
			this.freeDiskGb = freeDiskGb;
			return self();
		}

		public T freeRamMb(int freeRamMb) {
			this.freeRamMb = freeRamMb;
			return self();
		}

		public T hypervisorType(String hypervisorType) {
			this.hypervisorType = hypervisorType;
			return self();
		}

		public T hypervisorVersion(int hypervisorVersion) {
			this.hypervisorVersion = hypervisorVersion;
			return self();
		}

		public T localGb(int localGb) {
			this.localGb = localGb;
			return self();
		}

		public T localGbUsed(int localGbUsed) {
			this.localGbUsed = localGbUsed;
			return self();
		}

		public T memoryMb(int memoryMb) {
			this.memoryMb = memoryMb;
			return self();
		}

		public T memoryMbUsed(int memoryMbUsed) {
			this.memoryMbUsed = memoryMbUsed;
			return self();
		}

		public T runningVms(int runningVms) {
			this.runningVms = runningVms;
			return self();
		}

		public T vcpus(int vcpus) {
			this.vcpus = vcpus;
			return self();
		}

		public T vcpusUsed(int vcpusUsed) {
			this.vcpusUsed = vcpusUsed;
			return self();
		}

		public Hypervisor build() {
			return new Hypervisor(id, hypervisorHost,currentWorkload,diskAvailableLeast,freeDiskGb,freeRamMb, hypervisorType,hypervisorVersion,localGb,localGbUsed,memoryMb,memoryMbUsed,runningVms,vcpus,vcpusUsed);
		}

		public T fromHypervisor(Hypervisor h) {
			return this.id(h.getId()).hypervisorHost(h.getHypervisorHost())
					.currentWorkload(h.getCurrentWorkload())
					.diskAvailableLeast(h.getDiskAvailableLeast())
					.freeDiskGb(h.getFreeDiskGb()).freeRamMb(h.getFreeRamMb())
					.hypervisorType(h.getHypervisorType())
					.hypervisorVersion(h.getHypervisorVersion())
					.localGb(h.getLocalGb())
					.localGbUsed(h.getLocalGbUsed())
					.memoryMb(h.getMemoryMb())
					.memoryMbUsed(h.getMemoryMb())
					.runningVms(h.getRunningVms())
					.vcpus(h.getVcpus())
					.vcpusUsed(h.getVcpusUsed());
		}
	}

	private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
		@Override
		protected ConcreteBuilder self() {
			return this;
		}
	}

	private final String id;
	@Named("hypervisor_hostname")
	private final String hypervisorHost;
	@Named("current_workload")
	private final int currentWorkload;
	@Named("disk_available_least")
	private final int diskAvailableLeast;
	@Named("free_disk_gb")
	private final int freeDiskGb;
	@Named("free_ram_mb")
	private final int freeRamMb;
	@Named("hypervisor_type")
	private final String hypervisorType;
	@Named("hypervisor_version")
	private final int hypervisorVersion;
	@Named("local_gb")
	private final int localGb;
	@Named("local_gb_used")
	private final int localGbUsed;
	@Named("memory_mb")
	private final int memoryMb;
	@Named("memory_mb_used")
	private final int memoryMbUsed;
	@Named("running_vms")
	private final int runningVms;
	private final int vcpus;
	@Named("vcpus_used")
	private final int vcpusUsed;

	@ConstructorProperties({ "id", "hypervisor_hostname", "current_workload","disk_available_least", "free_disk_gb", "free_ram_mb","hypervisor_type", "hypervisor_version","local_gb", "local_gb_used", "memory_mb", "memory_mb_used","running_vms", "vcpus", "vcpus_used" })
	protected Hypervisor(String id, String hypervisorHost, int currentWorkload,int diskAvailableLeast, int freeDiskGb, int freeRamMb,@Nullable String hypervisorType, int hypervisorVersion, int localGb,int localGbUsed, int memoryMb, int memoryMbUsed, int runningVms,int vcpus, int vcpusUsed) {
		this.id = checkNotNull(id, "id");
		this.hypervisorHost = checkNotNull(hypervisorHost, "hypervisorHost");
		this.currentWorkload = checkNotNull(currentWorkload, "currentWorkload");
		this.diskAvailableLeast = checkNotNull(diskAvailableLeast,"diskAvailableLeast");
		this.freeDiskGb = checkNotNull(freeDiskGb, "freeDiskGb");
		this.freeRamMb = checkNotNull(freeRamMb, "freeRamMb");
		this.hypervisorType = hypervisorType;
		this.hypervisorVersion = checkNotNull(hypervisorVersion,"hypervisorVersion");
		this.localGb = checkNotNull(localGb, "localGb");
		this.localGbUsed = checkNotNull(localGbUsed, "localGbUsed");
		this.memoryMb = checkNotNull(memoryMb, "memoryMb");
		this.memoryMbUsed = checkNotNull(memoryMbUsed, "memoryMbUsedocalGb");
		this.runningVms = checkNotNull(runningVms, "runningVms");
		this.vcpus = checkNotNull(vcpus, "vcpus");
		this.vcpusUsed = checkNotNull(vcpusUsed, "vcpusUsed");
	}

	public String getId() {
		return id;
	}

	public String getHypervisorHost() {
		return hypervisorHost;
	}

	public int getCurrentWorkload() {
		return currentWorkload;
	}

	public int getDiskAvailableLeast() {
		return diskAvailableLeast;
	}

	public int getFreeDiskGb() {
		return freeDiskGb;
	}

	public int getFreeRamMb() {
		return freeRamMb;
	}

	public String getHypervisorType() {
		return hypervisorType;
	}

	public int getHypervisorVersion() {
		return hypervisorVersion;
	}

	public int getLocalGb() {
		return localGb;
	}

	public int getLocalGbUsed() {
		return localGbUsed;
	}

	public int getMemoryMb() {
		return memoryMb;
	}

	public int getMemoryMbUsed() {
		return memoryMbUsed;
	}

	public int getRunningVms() {
		return runningVms;
	}

	public int getVcpus() {
		return vcpus;
	}

	public int getVcpusUsed() {
		return vcpusUsed;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, hypervisorHost,currentWorkload,diskAvailableLeast,freeDiskGb,freeRamMb, hypervisorType,hypervisorVersion, localGb,localGbUsed,memoryMb,memoryMbUsed,runningVms,vcpus,vcpusUsed);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hypervisor other = (Hypervisor) obj;
		if (hypervisorHost == null) {
			if (other.hypervisorHost != null)
				return false;
		} else if (!hypervisorHost.equals(other.hypervisorHost))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return Objects.equal(this.id, other.id)
				&& Objects.equal(this.hypervisorHost, other.hypervisorHost)
				&& Objects.equal(this.currentWorkload, other.currentWorkload)
				&& Objects.equal(this.diskAvailableLeast, other.diskAvailableLeast)
				&& Objects.equal(this.freeDiskGb, other.freeDiskGb)
				&& Objects.equal(this.freeRamMb, other.freeRamMb)
				&& Objects.equal(this.hypervisorType, other.hypervisorType)
				&& Objects.equal(this.hypervisorVersion, other.hypervisorVersion)
				&& Objects.equal(this.localGb, other.localGb)
				&& Objects.equal(this.localGbUsed, other.localGbUsed)
				&& Objects.equal(this.memoryMb, other.memoryMb)
				&& Objects.equal(this.memoryMbUsed, other.memoryMbUsed)
				&& Objects.equal(this.runningVms, other.runningVms)
				&& Objects.equal(this.vcpus, other.vcpus)
				&& Objects.equal(this.vcpusUsed, other.vcpusUsed);
		// return true;
	}

	protected ToStringHelper string() {
		return Objects.toStringHelper(this).add("id", id)
				.add("hypervisorHost", hypervisorHost).add("currentWorkload", currentWorkload).add("diskAvailableLeast",diskAvailableLeast).add("freeDiskGb",freeDiskGb).add("freeRamMb",freeRamMb).add("hypervisorType",hypervisorType)
				.add("hypervisorVersion",hypervisorVersion).add("localGb",localGb).add("localGbUsed",localGbUsed).add("memoryMb",memoryMb).add("memoryMbUsed",memoryMbUsed).add("runningVms",runningVms).add("vcpus", vcpus).add("vcpusUsed",vcpusUsed);
	}

	@Override
	public String toString() {
		return string().toString();
	}

}
