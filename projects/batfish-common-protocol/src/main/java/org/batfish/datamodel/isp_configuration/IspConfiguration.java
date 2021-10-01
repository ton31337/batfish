package org.batfish.datamodel.isp_configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import static com.google.common.base.MoreObjects.firstNonNull;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * Specification for modeling ISPs for a network snapshot. There are three parts to this
 * specification.
 *
 * <ol>
 *   <li>How/where ISPs connect to the snapshot, which can be done via either {@link
 *       BorderInterfaceInfo} or {@link BgpPeerInfo}, each of which have their own semantics.
 *       Batfish combines the two.
 *   <li>A filter on which subset of ISPs, specified using mechanisms above, should be modeled. This
 *       is specified via {@link IspFilter}.
 *   <li>Information about each ISP, which helps control its names, how it interacts witt the
 *       Internet, etc. This is provided via {@link IspNodeInfo}.
 * </ol>
 */
public class IspConfiguration {
  private static final String PROP_BORDER_INTERFACES = "borderInterfaces";
  private static final String PROP_BGP_PEERS = "bgpPeers";
  private static final String PROP_FILTER = "filter";
  private static final String PROP_ISP_NODE_INFO = "ispNodeInfo";

  @Nonnull private final List<BorderInterfaceInfo> _borderInterfaces;
  @Nonnull private final List<BgpPeerInfo> _bgpPeersInfos;
  @Nonnull private final IspFilter _filter;
  @Nonnull private final List<IspNodeInfo> _ispNodeInfos;

  public IspConfiguration(
      @Nonnull List<BorderInterfaceInfo> borderInterfaces, @Nonnull IspFilter filter) {
    this(borderInterfaces, ImmutableList.of(), filter, ImmutableList.of());
  }

  public IspConfiguration(
      @Nonnull List<BorderInterfaceInfo> borderInterfaces,
      @Nonnull List<BgpPeerInfo> bgpPeerInfos,
      @Nonnull IspFilter filter,
      @Nonnull List<IspNodeInfo> ispNodeInfos) {
    _borderInterfaces = ImmutableList.copyOf(borderInterfaces);
    _bgpPeersInfos = ImmutableList.copyOf(bgpPeerInfos);
    _filter = filter;
    _ispNodeInfos = ispNodeInfos;
  }

  @JsonCreator
  private static IspConfiguration jsonCreator(
      @JsonProperty(PROP_BORDER_INTERFACES) @Nullable
          List<BorderInterfaceInfo> borderInterfaceInfos,
      @JsonProperty(PROP_BGP_PEERS) @Nullable List<BgpPeerInfo> bgpPeerInfos,
      @JsonProperty(PROP_FILTER) @Nullable IspFilter filter,
      @JsonProperty(PROP_ISP_NODE_INFO) @Nullable List<IspNodeInfo> ispNodeInfos) {
    return new IspConfiguration(
        firstNonNull(borderInterfaceInfos, ImmutableList.of()),
        firstNonNull(bgpPeerInfos, ImmutableList.of()),
        firstNonNull(filter, IspFilter.ALLOW_ALL),
        firstNonNull(ispNodeInfos, ImmutableList.of()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IspConfiguration)) {
      return false;
    }
    IspConfiguration that = (IspConfiguration) o;
    return Objects.equals(_borderInterfaces, that._borderInterfaces)
        && Objects.equals(_bgpPeersInfos, that._bgpPeersInfos)
        && Objects.equals(_filter, that._filter)
        && Objects.equals(_ispNodeInfos, that._ispNodeInfos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_borderInterfaces, _bgpPeersInfos, _filter, _ispNodeInfos);
  }

  @JsonProperty(PROP_BORDER_INTERFACES)
  @Nonnull
  public List<BorderInterfaceInfo> getBorderInterfaces() {
    return _borderInterfaces;
  }

  @JsonProperty(PROP_BGP_PEERS)
  @Nonnull
  public List<BgpPeerInfo> getBgpPeerInfos() {
    return _bgpPeersInfos;
  }

  @JsonProperty(PROP_FILTER)
  @Nonnull
  public IspFilter getFilter() {
    return _filter;
  }

  @JsonProperty(PROP_ISP_NODE_INFO)
  @Nonnull
  public List<IspNodeInfo> getIspNodeInfos() {
    return _ispNodeInfos;
  }
}
