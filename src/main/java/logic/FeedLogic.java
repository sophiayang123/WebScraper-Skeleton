/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import dao.FeedDAO;
import entity.Feed;
import entity.Host;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 *
 * @author shutingyang
 */
public class FeedLogic extends GenericLogic<Feed, FeedDAO> {
    public final static String ID = "id";
    public final static String PATH = "path";
    public final static String TYPE = "type";
    public final static String NAME = "name";
    public final static String HOST_ID = "hostId";
    
    public FeedLogic(){
        super(new FeedDAO());
    }
    
    @Override
    public List<Feed> getAll(){
        return get(()->dao().findAll());
    }
    
    @Override
    public Feed getWithId(int id){
        return get(()->dao().findById(id));
    }
    
    
    public List<Feed> search(String search){
        return get(()->dao().findConaining(search));
    }
    
    public Feed getHostWithName( String name){
        return get(()->dao().findByName(name));
    }

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("id", "path","name","hostID");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, PATH, NAME, HOST_ID);
    }

    @Override
    public List<?> extractDataAsList(Feed e) {
        return Arrays.asList(e.getId(), e.getName(), e.getHostid(), e.getPath(),e.getType());
    }

    @Override
    public Feed createEntity(Map<String, String[]> parameterMap) {
        Feed feed = new Feed();
        if(parameterMap.containsKey(ID)){
            feed.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        feed.setName(parameterMap.get(NAME)[0]);
        feed.setPath(parameterMap.get(PATH)[0]);
//        feed.setHostid(parameterMap.get(HOST_ID)[0]);
        feed.setType(parameterMap.get(TYPE)[0]);
        return feed;
    }
}
