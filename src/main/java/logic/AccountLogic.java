/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import dao.AccountDAO;
import entity.Account;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author shutingyang
 */
public class AccountLogic extends GenericLogic<Account, AccountDAO> {
    public final static String DISPLAY_NAME = "firstName";
    public final static String USER = "lastName";
    public final static String PASSWORD = "joined";
    public final static String ID = "id";

     public AccountLogic() {
        super( new AccountDAO());
    }
     
    @Override
    public List<Account> getAll() {
        return get(()->dao().findAll());
    }

    @Override
    public Account getWithId(int id) {
        return get(()->dao().findById(id));
    }
     
    
    public Account getAccountWithDisplayName(String displayName){
        return get(()->dao().findByDisplayName(displayName));
    }
    
    public Account getAccountWithUser(String user){
        return get(()->dao().findByUser(user));
    }
    
    public List<Account> getAccountsWithPassword(String password){
        return get(()->dao().findByPassword(password));
    }
    
    public Account getAccountWith(String username, String password){
        return get(()->dao().validateUser(username, password));
    }
    
    @Override
    public List<Account> search(String search){
        return get(()->dao().findContaining(search));
    }
    
    @Override
    public Account createEntity(Map<String, String[]> parameterMap){
        Account account = new Account();
        if(parameterMap.containsKey(ID)){
            account.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        account.setDisplayName(parameterMap.get(DISPLAY_NAME)[0]);
        account.setUser(parameterMap.get(USER)[0]);
        account.setPassword(parameterMap.get(PASSWORD)[0]);
        
        return account;
    }
    
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("USER", "ID", "DISPLAY_NAME", "PASSWORD");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(USER, ID, DISPLAY_NAME, PASSWORD);
    }
     
    @Override
    public List<?> extractDataAsList(Account e){
        return Arrays.asList(e.getId(), e.getUser(), e.getDisplayName(), e.getPassword());
    }
    

}
