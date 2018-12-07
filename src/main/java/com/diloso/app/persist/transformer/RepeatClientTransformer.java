package com.diloso.app.persist.transformer;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diloso.app.negocio.dao.ClientDAO;
import com.diloso.app.negocio.dao.RepeatDAO;
import com.diloso.app.negocio.dto.ClientDTO;
import com.diloso.app.negocio.dto.RepeatClientDTO;
import com.diloso.app.negocio.dto.RepeatDTO;
import com.diloso.app.persist.entities.RepeatClient;


@Component
@Scope(value = "singleton")
public class RepeatClientTransformer {
	
	public RepeatClientTransformer() {

	}
	
	@Autowired
	protected RepeatDAO repeatDAO;
	
	@Autowired
	protected ClientDAO clientDAO;

	public RepeatClient transformDTOToEntity(RepeatClientDTO repeatClient){
		
		RepeatClient entityRepeatClient = new RepeatClient();
		
		try {
			PropertyUtils.copyProperties(entityRepeatClient, repeatClient);
		} catch (Exception e) {
		} 
		
		if (repeatClient.getRecRepeat()!=null){
			entityRepeatClient.setRecRepeatId(repeatClient.getRecRepeat().getId());
		}
		if (repeatClient.getRecClient()!=null){
			entityRepeatClient.setRecClientId(repeatClient.getRecClient().getId());
		}
		
		return entityRepeatClient;
	}
	
	public RepeatClientDTO transformEntityToDTO(RepeatClient entityRepeatClient) {

		RepeatClientDTO repeatClient = new RepeatClientDTO();

		try {
			PropertyUtils.copyProperties(repeatClient, entityRepeatClient);
			
			// Propiedades de Repeat
			if (entityRepeatClient.getRecRepeatId() != null) {
				RepeatDTO repeat = repeatDAO
						.getById(entityRepeatClient.getRecRepeatId());
					repeatClient.setRecRepeat(repeat);
			}
			
			// Propiedades de Client
			if (entityRepeatClient.getRecClientId() != null) {
				ClientDTO client = clientDAO
						.getById(entityRepeatClient.getRecClientId());
				repeatClient.setRecClient(client);
			}
			
		} catch (Exception e) {
		}
		return repeatClient;
	}
	
		
}
