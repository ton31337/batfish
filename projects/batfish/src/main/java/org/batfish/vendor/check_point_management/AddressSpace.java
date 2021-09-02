package org.batfish.vendor.check_point_management;

/** An object that may be used to represent an address space. */
public interface AddressSpace {

  <T> T accept(AddressSpaceVisitor<T> visitor);
}
