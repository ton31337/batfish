package org.batfish.datamodel.isp_configuration;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import org.batfish.datamodel.Ip;

/**
 * BGP peer information used to create sessions to modeled ISPs. This specification identifies a BGP
 * peer in the snapshot via 1) the hostname, and 2) either a peer address or interface (for
 * unnumbered peers), and 3) vrf (optional, needed to resolve ambiguity with peer addresses).
 *
 * <p>The specification also allows for describing new connectivity needed to establish the session,
 * via {@link IspAttachment}. The BGP peering is not modeled if this new connectivity conflicts with
 * other information (e.g., L1 links conflict with existing L1).
 */
@ParametersAreNonnullByDefault
public class BgpPeerInfo {
  private static final String PROP_HOSTNAME = "hostname";
  private static final String PROP_INTERFACE = "interface";
  private static final String PROP_PEER_ADDRESS = "peerAddress";
  private static final String PROP_VRF = "vrf";
  private static final String PROP_CONNECTIVITY = "connectivity";

  @Nonnull private final String _hostname;
  @Nullable private final String _iface;
  @Nullable private final Ip _peerAddress;
  @Nullable private final String _vrf;
  @Nullable private final IspAttachment _ispAttachment;

  public BgpPeerInfo(
      String hostname,
      @Nullable String iface,
      @Nullable Ip peerAddress,
      @Nullable String vrf,
      @Nullable IspAttachment bgpPeerConnectivity) {
    checkArgument(
        iface != null || peerAddress != null,
        "Either one of %s or %s should be specified",
        PROP_INTERFACE,
        PROP_PEER_ADDRESS);
    checkArgument(
        iface == null || peerAddress == null,
        "Both %s or %s should not be specified",
        PROP_INTERFACE,
        PROP_PEER_ADDRESS);
    _hostname = hostname;
    _iface = iface;
    _peerAddress = peerAddress;
    _vrf = vrf;
    _ispAttachment = bgpPeerConnectivity;
  }

  @JsonCreator
  private static BgpPeerInfo jsonCreator(
      @JsonProperty(PROP_HOSTNAME) @Nullable String hostname,
      @JsonProperty(PROP_INTERFACE) @Nullable String iface,
      @JsonProperty(PROP_PEER_ADDRESS) @Nullable Ip peerAddress,
      @JsonProperty(PROP_VRF) @Nullable String vrf,
      @JsonProperty(PROP_CONNECTIVITY) @Nullable IspAttachment bgpPeerConnectivity) {
    checkArgument(hostname != null, "Missing %s", PROP_HOSTNAME);
    return new BgpPeerInfo(hostname, iface, peerAddress, vrf, bgpPeerConnectivity);
  }
}
