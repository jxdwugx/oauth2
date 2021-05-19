package com.example.consistent;

import java.util.*;

/**
 * @description
 * @autor 吴光熙
 * @date 2021/5/18  19:57
 **/
public class ConsistentHash<T> {
    private SortedMap<Long, T> map = new TreeMap<>();

    private final HashFunction hashFunction;

    private final Integer virtualNodeNum;

    public ConsistentHash(HashFunction hashFunction, Integer virtualNodeNum, Collection<T> nodeList) {
        this.hashFunction = hashFunction;
        this.virtualNodeNum = virtualNodeNum;
        nodeList.forEach(this::addNode);
    }

    public void addNode(T node){
        for(int i = 0; i < virtualNodeNum; i++){
            map.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    public void removeNode(T node){
        for(int i = 0; i < virtualNodeNum; i++){
            map.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public T getNode(String key){
        if(map.isEmpty()){
            return null;
        }
        Long hash = hashFunction.hash(key);
        SortedMap<Long, T> tailMap = map.tailMap(hash);
        if(tailMap.isEmpty()){
            return map.get(map.firstKey());
        }
        return map.get(tailMap.firstKey());
    }

    public static void main(String[] args) {
        Set<String> nodes = new HashSet<>();
        nodes.add("A");
        ConsistentHash<String> consistentHash = new ConsistentHash<>(new GenericHashFunction(), 5, nodes);
        System.out.println(consistentHash.getNode("B"));
        consistentHash.addNode("B");
        System.out.println(consistentHash.getNode("B"));
        consistentHash.removeNode("B");
        System.out.println(consistentHash.getNode("B"));
    }
}
