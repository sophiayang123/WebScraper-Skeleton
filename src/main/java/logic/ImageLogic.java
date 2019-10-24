/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import dao.ImageDAO;
import entity.Image;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author shutingyang
 */
public class ImageLogic extends GenericLogic<Image, ImageDAO>{
    public static String ID = "id";
    public static String PATH ="path";
    public static String NAME = "name";
    public static String DATE = "date";
    public static String FEED_ID = "feedid";
    
    public ImageLogic(){
        super(new ImageDAO());
    }
    
    @Override
    public List<Image> getAll(){
        return get(()-> dao().findAll());
    }
    
    @Override
    public Image getWithId(int id){
        return get(()->dao().findById(id));
    }
    
    public List<Image> getImagesWithFeedId(int feedId){
        return get(()->dao().findByFeedId(feedId));
    }
    
    public List<Image> getImageWithName(String name){
        return get(()->dao().findByName(name));
    }
    
    public Image getImageWithPath(String path){
        return get(()->dao().findByPath(path));
    }
    
    public List<Image> getImageWithDate(Date date){
        return get(()->dao().findByDate(date));
    }
    
    public List<Image> search(String search){
        return get(()->dao().findContaining(search));
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("id", "path","name","date","feedid");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, NAME, PATH, DATE, FEED_ID);
    }

    @Override
    public List<?> extractDataAsList(Image e) {
        return Arrays.asList(e.getId(), e.getName(), e.getDate(), e.getFeedid(), e.getPath());
    }

    @Override
    public Image createEntity(Map<String, String[]> parameterMap) {
        Image image = new Image();
        if(parameterMap.containsKey(ID)){
            image.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        image.setName(parameterMap.get(NAME)[0]);
        image.setPath(parameterMap.get(PATH)[0]);
        //image.setDate(parameterMap.get(DATE)[0]);
        //image.setFeedid(parameterMap.get(FEED_ID)[0]);
        
        return image;
    }    
}
