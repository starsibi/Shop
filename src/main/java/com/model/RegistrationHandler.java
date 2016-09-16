package com.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;

import com.service.RegisterService;

@Component
public class RegistrationHandler {

	public RegistrationDetails  startFlow() {
		
		System.out.println("RegistrationDetail objects gets created");
		return new RegistrationDetails();
	}
	
	@Autowired
	EmailAPI mailtouser;
	public void send(RegistrationDetails registrationDetails)
	{
	
		String toAddr = registrationDetails.getMail_id();
		String fromAddr = "clickdslr@hotmail.com";
 
		// email subject
		String subject = "Clickart welcomes you";
 
		// email body
		String body = "Welcome to the Clickart "+registrationDetails.getName()+"."+System.getProperty("line.separator")
				+"Thanks to begin with us."+System.getProperty("line.separator")
				+System.getProperty("line.separator")
				+System.getProperty("line.separator")
				+System.getProperty("line.separator")
				+ "------------"+System.getProperty("line.separator")
				+ "-Clickart";
		mailtouser.mailmethod(toAddr, fromAddr, subject, body);
		
		
	}
	@Autowired 
	RegisterService regser;
	
	public  void saveRegister(RegistrationDetails regDetails){
		
		regser.saveOrUpdate(regDetails);
		
	}
	
	
	public String validateDetails(RegistrationDetails newuser,MessageContext messageContext){
		String status = "success";
		if(newuser.getName().isEmpty()){
			messageContext.addMessage(new MessageBuilder().error().source(
					"fname").defaultText("Name Cannot Be Empty cannot be Empty").build());
			status = "failure";
		}
		if(newuser.getMail_id().isEmpty()){
			messageContext.addMessage(new MessageBuilder().error().source(
					"email").defaultText("Email cannot be Empty").build());
			status = "failure";
		}
		
		if(newuser.getPassword().isEmpty()){
			messageContext.addMessage(new MessageBuilder().error().source(
					"password").defaultText("password cannot be Empty").build());
			status = "failure";
		}
		if(newuser.getConfirm_password().isEmpty()){
			{
				if(newuser.getPassword() != (newuser.getConfirm_password()))
					
			messageContext.addMessage(new MessageBuilder().error().source(
					"cPassword").defaultText("Conformation password not valid").build());
			status = "failure";
			}
		}
		if(newuser.getPassword().isEmpty()){
			messageContext.addMessage(new MessageBuilder().error().source(
					"phnumber").defaultText("PHONE NUMBER cannot be Empty").build());
			status = "failure";
		}
			
		return status;
	}
}
	
