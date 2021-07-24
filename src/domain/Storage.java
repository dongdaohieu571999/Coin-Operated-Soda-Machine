package domain;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Storage<T>{
    private Map<T,Integer> storage = new HashMap<>();

    public void deduct(T item){
        Integer amount = storage.get(item);
        if(isRemainItem(item)){
            storage.put(item,amount-1);
        }
    }

    public void add(T item){
        Integer amount = storage.get(item);
        storage.put(item,amount+1);
    }

    public void put(T item, Integer i){
        storage.put(item, i);
    }

    public boolean isRemainItem(T item){
        return !storage.get(item).equals(0);
    }

    public Map<T, Integer> getStorage(){
        return storage;
    }

    public Storage<T> cloneStorage(){
        Storage<T> storageClone = new Storage<>();
        storageClone.storage = new HashMap<>(this.storage);
        return storageClone;
    }
}
