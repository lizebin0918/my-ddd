package com.lzb.component.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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
        Map<String, Object> working = Map.of("item", "foo", "same", "same", "name", "lizebin", "bigdecimal", new BigDecimal("1.0"), "list", List.of(1, 2, 3));
        Map<String, Object> base = Map.of("item", "bar", "same", "same", "bigdecimal", BigDecimal.ONE, "list", List.of(1, 2));
        DiffNode diff = ObjectDifferBuilder.startBuilding()
                .comparison()
                .ofType(BigDecimal.class)
                .toUseCompareToMethod()
                .and()
                .build()
                .compare(working, base);
        //DiffNode diff = ObjectDifferBuilder.buildDefault().compare(working, base);

        System.out.println(diff.hasChanges());
        System.out.println(diff.childCount() == 1);
        NodePath itemPath = NodePath.startBuilding().mapKey("item").build();
        System.out.println(diff.getChild(itemPath).getState() == DiffNode.State.CHANGED);

        // 递归遍历
        System.out.println("递归遍历------");
        diff.visit(new DiffNode.Visitor()
        {
            @Override
            public void node(DiffNode diffNode, Visit visit) {
                System.out.println(diffNode.getPath() + " => " + diffNode.getState());
            }
        });
        System.out.println("递归遍历------");

        // 读取
        System.out.println("读取--------");
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
        System.out.println("读取--------");
    }

    @Test
    @DisplayName("对比数组")
    void should_compare_array() {
        String[] array1 = {"apple", "banana", "orange"};
        List<String> list1 = List.of("apple", "banana", "orange");
        String[] array2 = {"apple", "grape", "orange"};
        List<String> list2 = List.of("apple", "banana", "orange");

        System.out.println(list1.equals(list2));

        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(list2, list1);
        // DiffNode diff = ObjectDifferBuilder.startBuilding().comparison().ofType(List.class).toUseEqualsMethod().and().comparison().ofType().build().compare(list1, list2);

        diff.visit(new DiffNode.Visitor() {
            @Override
            public void node(DiffNode node, Visit visit) {
                if (node.isChanged()) {
                    System.out.println("Array element changed at path: " + node.getPath());
                    System.out.println("Old value: " + Arrays.toString((Object[]) node.canonicalGet(array1)));
                    System.out.println("New value: " + Arrays.toString((Object[]) node.canonicalGet(array2)));
                }
            }
        });
    }

}
