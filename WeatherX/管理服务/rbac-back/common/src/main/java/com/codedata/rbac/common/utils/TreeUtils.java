package com.codedata.rbac.common.utils;

import com.codedata.rbac.common.base.BaseTreeNode;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class TreeUtils {
    /**
     * 根据pid，构建树节点
     */
    public static <T extends BaseTreeNode> List<T> build(List<T> treeNodes, Long pid) {
        if(pid==null){
            throw new ServiceException(ErrorCode.ERR_MENU,"构建基础树父级id不能为空");
        }
        List<T> treeList = new ArrayList<>();
        for(T treeNode : treeNodes) {
            if (pid.equals(treeNode.getPid())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }
        return treeList;
    }

    /**
     * 查找子节点
     */
    private static <T extends BaseTreeNode> T findChildren(List<T> treeNodes, T rootNode) {
        for(T treeNode : treeNodes) {
            if(rootNode.getId().equals(treeNode.getPid())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     */
    public static <T extends BaseTreeNode> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<T>();

        //list转map
        Map<Long, T> nodeMap = new LinkedHashMap<Long, T>(treeNodes.size());
        for(T treeNode : treeNodes){
            nodeMap.put(treeNode.getId(), treeNode);
        }

        for(T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getPid());
            if(parent != null && !(node.getId().equals(parent.getId()))){
                parent.getChildren().add(node);
                continue;
            }

            result.add(node);
        }

        return result;
    }
}
