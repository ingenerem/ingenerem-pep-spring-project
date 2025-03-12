package com.example.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {


  private AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository){
    this.accountRepository = accountRepository;
  }


   /**
     * A method to create a new user account
     * @param acc The account to be created
     * @return The account that was created or null if failed
     */
  public Account register(Account acc){
    Optional<Account> accountOptional = accountRepository.findByUsername(acc.getUsername());
    if (!isValidAccount(acc) || accountOptional.isPresent()) {
         return null;
     }
    return accountRepository.save(acc);
    

  }

  /**
     * A method to check if a given account is valid
     * @param acc account to be validated
     * @return True if the account details constitute a valid account
   */
  private boolean isValidAccount(Account acc){
        if(acc == null || acc.getUsername()==null || acc.getUsername().length()==0 || acc.getPassword()==null || acc.getPassword().length()<4 )
          return false;
        return true;

    }

    /**
     * A method to login the user
     * @param acc User account to be logged in
     * @return The logged in account or null if login failed
     */
    public Account login(Account acc){

      return accountRepository.findByUsernameAndPassword(acc.getUsername(), acc.getPassword()).orElse(null);

    }


     /**
     * A method to check if the poster's user_id exists in the database 
     * @param posted_by The user_id to verify
     * @return true if the user_id is found and false otherwise
     */
    public Boolean isValidUser(Integer posted_by){
      return accountRepository.findById(posted_by).isPresent();
  }

}
