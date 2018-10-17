package hello;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class CategoryImpl extends EntityImpl implements Category {

  @Relationship(type = "HAS_LABEL")
  private Text label;

  @Override
  public Text getLabel() {
    return label;
  }

  @Override
  public void setLabel(Text label) {
    this.label = label;
  }
}
