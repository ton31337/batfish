package org.batfish.representation.palo_alto;

import com.google.common.collect.ImmutableSortedSet;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import org.batfish.datamodel.LineAction;

/** PAN datamodel component containing security rule configuration */
@ParametersAreNonnullByDefault
public final class SecurityRule implements Serializable {

  public enum RuleType {
    INTERZONE,
    INTRAZONE,
    UNIVERSAL
  }

  // Name of the rule
  @Nonnull private final String _name;
  // Action of the rule
  @Nonnull private LineAction _action;
  // Owning Vsys of this rule
  @Nonnull private final Vsys _vsys;

  // Description of the rule
  @Nullable private String _description;

  private boolean _disabled;

  // Zones
  @Nonnull private final SortedSet<String> _from;
  @Nonnull private final SortedSet<String> _to;

  // IPs
  @Nonnull private final List<RuleEndpoint> _source;
  @Nonnull private final List<RuleEndpoint> _destination;
  private boolean _negateSource;
  private boolean _negateDestination;

  // Services
  @Nonnull private final SortedSet<ServiceOrServiceGroupReference> _service;

  // Applications
  @Nonnull private final SortedSet<ApplicationOrApplicationGroupReference> _applications;

  // Rule type
  @Nullable private RuleType _ruleType;

  @Nonnull private final Set<String> _tags;

  public SecurityRule(String name, Vsys vsys) {
    _action = LineAction.DENY;
    _applications = new TreeSet<>();
    _destination = new LinkedList<>();
    _negateDestination = false;
    _disabled = false;
    _from = new TreeSet<>();
    _service = new TreeSet<>();
    _source = new LinkedList<>();
    _negateSource = false;
    _to = new TreeSet<>();
    _tags = new HashSet<>(1);
    _name = name;
    _vsys = vsys;
  }

  @Nonnull
  public String getName() {
    return _name;
  }

  @Nonnull
  public LineAction getAction() {
    return _action;
  }

  public void addApplication(String application) {
    _applications.add(new ApplicationOrApplicationGroupReference(application));
  }

  @Nonnull
  public SortedSet<ApplicationOrApplicationGroupReference> getApplications() {
    return ImmutableSortedSet.copyOf(_applications);
  }

  @Nullable
  public String getDescription() {
    return _description;
  }

  @Nonnull
  public List<RuleEndpoint> getDestination() {
    return _destination;
  }

  public boolean getDisabled() {
    return _disabled;
  }

  @Nonnull
  public SortedSet<String> getFrom() {
    return _from;
  }

  public boolean getNegateDestination() {
    return _negateDestination;
  }

  public void setNegateDestination(boolean negateDestination) {
    _negateDestination = negateDestination;
  }

  public boolean getNegateSource() {
    return _negateSource;
  }

  public void setNegateSource(boolean negateSource) {
    _negateSource = negateSource;
  }

  @Nonnull
  public SortedSet<ServiceOrServiceGroupReference> getService() {
    return _service;
  }

  @Nonnull
  public List<RuleEndpoint> getSource() {
    return _source;
  }

  @Nonnull
  public SortedSet<String> getTo() {
    return _to;
  }

  @Nonnull
  public Set<String> getTags() {
    return _tags;
  }

  @Nonnull
  public Vsys getVsys() {
    return _vsys;
  }

  @Nullable
  public RuleType getRuleType() {
    return _ruleType;
  }

  public void setAction(LineAction action) {
    _action = action;
  }

  public void setDescription(String description) {
    _description = description;
  }

  public void setDisabled(boolean disabled) {
    _disabled = disabled;
  }

  public void setRuleType(@Nullable RuleType ruleType) {
    _ruleType = ruleType;
  }
}
