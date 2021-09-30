package org.batfish.common.util.isp;

import com.google.common.base.MoreObjects;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

/** Represents one connection (interface(s), BGP peer) this ISP has to the snapshot */
final class SnapshotConnection {

  private @Nonnull final String _snapshotHostname;
  private @Nonnull final List<IspInterface> _interfaces;
  private @Nonnull final IspBgpPeer _bgpPeer;

  SnapshotConnection(String snapshotHostname, List<IspInterface> interfaces, IspBgpPeer bgpPeer) {
    _snapshotHostname = snapshotHostname;
    _interfaces = interfaces;
    _bgpPeer = bgpPeer;
  }

  @Nonnull
  public String getSnapshotHostname() {
    return _snapshotHostname;
  }

  @Nonnull
  public List<IspInterface> getInterfaces() {
    return _interfaces;
  }

  @Nonnull
  public IspBgpPeer getBgpPeer() {
    return _bgpPeer;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SnapshotConnection)) {
      return false;
    }
    SnapshotConnection that = (SnapshotConnection) o;
    return _snapshotHostname.equals(that._snapshotHostname)
        && _interfaces.equals(that._interfaces)
        && _bgpPeer.equals(that._bgpPeer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_snapshotHostname, _interfaces, _bgpPeer);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("snapshotHostname", _snapshotHostname)
        .add("interfaces", _interfaces)
        .add("bgpPeerConfig", _bgpPeer)
        .toString();
  }
}
