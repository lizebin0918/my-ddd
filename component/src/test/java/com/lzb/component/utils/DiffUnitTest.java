package com.lzb.component.utils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import com.lzb.BaseUnitTest;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;
import de.danielbechler.diff.path.NodePath;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2023-09-23 13:54
 * @author mac
 */
public class DiffUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("")
    @Disabled
    void should_() {
        Map<String, Object> working = Map.of("item", "foo", "name", "lizebin", "bigdecimal", new BigDecimal("1.0"));
        Map<String, Object> base = Map.of("item", "bar", "bigdecimal", BigDecimal.ONE);
        //DiffNode diff = ObjectDifferBuilder.startBuilding().comparison().ofType(BigDecimal.class).toUseCompareToMethod().and().build().compare(working, base);
        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);

        System.out.println(diff.hasChanges());
        System.out.println(diff.childCount() == 1);
        NodePath itemPath = NodePath.startBuilding().mapKey("item").build();
        System.out.println(diff.getChild(itemPath).getState() == DiffNode.State.CHANGED);

        // 递归遍历
        diff.visit(new DiffNode.Visitor()
        {
            @Override
            public void node(DiffNode diffNode, Visit visit) {
                System.out.println(diffNode.getPath() + " => " + diffNode.getState());
            }
        });

        // 读取
        diff.visit(new DiffNode.Visitor()
        {
            public void node(DiffNode node, Visit visit)
            {
                final Object baseValue = node.canonicalGet(base);
                final Object workingValue = node.canonicalGet(working);
                final String message = node.getPath() + " changed from " +
                        baseValue + " to " + workingValue;
                System.out.println(message);
            }
        });
    }

}
