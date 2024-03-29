/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Account;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author shutingyang
 */


public class AccountDAO extends GenericDAO<Account> {
    public AccountDAO(){
        super(Account.class);
    }   
    
    public List<Account> findAll(){
        return findResults( "Account.findAll", null);
    }
    
    public Account findById(int id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return findResult( "Account.findById", map);
    }
    
    public Account findByDisplayName(String name){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return findResult( "Account.findByDisplayName", map);
    }
    
    public Account findByUser(String user){
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        return findResult( "Account.findByUser", map);
    }
    
    public List<Account> findByPassword(String password){
        Map<String, Object> map = new HashMap<>();
        map.put("password", password);
        return findResults( "Account.findByPassword", map);
    }
    
    public List<Account> findContaining(String search){
        return findResults( "Account.findAll", null);
    }
    
    public Account validateUser(String username, String pass){
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("pass",pass);
        return findResult( "Account.validateUser", map);
    }
}
