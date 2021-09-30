package org.batfish.common.util.isp;

import com.google.common.base.MoreObjects;
import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.batfish.datamodel.Prefix;
import org.batfish.datamodel.isp_configuration.traffic_filtering.IspTrafficFiltering;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Contains the information required to model one ISP node */
@ParametersAreNonnullByDefault
final class IspModel {

  static final class Builder {
    IspModel build() {
      checkArgument(_asn != null, "Missing ASN");
      boolean internetConnection = firstNonNull(_internetConnection, true);
      Set<Prefix> internetAnnouncements =
          firstNonNull(_additionalPrefixesToInternet, ImmutableSet.of());
      checkArgument(
          internetConnection || internetAnnouncements.isEmpty(),
          "Internet announcements should not be provided when internet connection is false");
      return new IspModel(
          _asn,
          firstNonNull(_snapshotConnections, ImmutableList.of()),
          _name,
          internetConnection,
          internetAnnouncements,
          firstNonNull(_trafficFiltering, IspTrafficFiltering.none()));
    }

    public Builder setAsn(long asn) {
      _asn = asn;
      return this;
    }

    public Builder setName(@Nullable String name) {
      _name = name;
      return this;
    }

    public Builder setInternetConnection(boolean internetConnection) {
      _internetConnection = internetConnection;
      return this;
    }

    public Builder setAdditionalPrefixesToInternet(
        @Nullable Iterable<Prefix> additionalPrefixesToInternet) {
      _additionalPrefixesToInternet =
          additionalPrefixesToInternet == null
              ? null
              : ImmutableSet.copyOf(additionalPrefixesToInternet);
      return this;
    }

    public Builder setAdditionalPrefixesToInternet(
        @Nonnull Prefix... additionalPrefixesToInternet) {
      return setAdditionalPrefixesToInternet(Arrays.asList(additionalPrefixesToInternet));
    }

    public Builder setSnapshotConnections(
        @Nullable Iterable<SnapshotConnection> snapshotConnections) {
      _snapshotConnections =
          snapshotConnections == null ? null : ImmutableList.copyOf(snapshotConnections);
      return this;
    }

    public Builder setSnapshotConnections(@Nonnull SnapshotConnection... snapshotConnections) {
      return setSnapshotConnections(Arrays.asList(snapshotConnections));
    }

    public Builder setTrafficFiltering(@Nullable IspTrafficFiltering trafficFiltering) {
      _trafficFiltering = trafficFiltering;
      return this;
    }

    private @Nullable Long _asn;
    private @Nullable String _name;
    private @Nullable List<SnapshotConnection> _snapshotConnections;
    private Boolean _internetConnection;
    private @Nullable Set<Prefix> _additionalPrefixesToInternet;
    private @Nullable IspTrafficFiltering _trafficFiltering;
  }

  public static @Nonnull Builder builder() {
    return new Builder();
  }

  private final long _asn;
  private final @Nullable String _name;
  private @Nonnull List<SnapshotConnection> _snapshotConnections;
  private final boolean _internetConnection;
  private final @Nonnull Set<Prefix> _additionalPrefixesToInternet;
  private final @Nonnull IspTrafficFiltering _trafficFiltering;

  private IspModel(
      long asn,
      List<SnapshotConnection> snapshotConnections,
      @Nullable String name,
      boolean internetConnection,
      Set<Prefix> additionalPrefixesToInternet,
      IspTrafficFiltering trafficFiltering) {
    _asn = asn;
    _snapshotConnections = snapshotConnections;
    _name = name;
    _internetConnection = internetConnection;
    _additionalPrefixesToInternet = ImmutableSet.copyOf(additionalPrefixesToInternet);
    _trafficFiltering = trafficFiltering;
  }

  @Nonnull
  List<SnapshotConnection> getSnapshotConnections() {
    return ImmutableList.copyOf(_snapshotConnections);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof IspModel)) {
      return false;
    }
    IspModel ispModel = (IspModel) o;
    return _asn == ispModel._asn
        && _internetConnection == ispModel._internetConnection
        && Objects.equals(_name, ispModel._name)
        && _snapshotConnections.equals(ispModel._snapshotConnections)
        && _additionalPrefixesToInternet.equals(ispModel._additionalPrefixesToInternet)
        && _trafficFiltering.equals(ispModel._trafficFiltering);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        _asn,
        _name,
        _snapshotConnections,
        _internetConnection,
        _additionalPrefixesToInternet,
        _trafficFiltering);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .omitNullValues()
        .add("asn", _asn)
        .add("name", _name)
        .add("neighbors", _snapshotConnections)
        .add("additionalPrefixes", _additionalPrefixesToInternet)
        .add("trafficFiltering", _trafficFiltering)
        .toString();
  }

  public long getAsn() {
    return _asn;
  }

  public @Nonnull String getHostname() {
    return IspModelingUtils.getDefaultIspNodeName(_asn);
  }

  public @Nullable String getName() {
    return _name;
  }

  public boolean getInternetConnection() {
    return _internetConnection;
  }

  /**
   * Returns the prefixes that the ISP should announce to the Internet over the BGP connection
   * (beyond just passing along what it hears from other connected nodes)
   */
  public @Nonnull Set<Prefix> getAdditionalPrefixesToInternet() {
    return _additionalPrefixesToInternet;
  }

  /** Returns the {@link IspTrafficFiltering traffic filtering policy} of this ISP. */
  public @Nonnull IspTrafficFiltering getTrafficFiltering() {
    return _trafficFiltering;
  }
}
