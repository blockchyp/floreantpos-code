/**
 * ************************************************************************
 * * The contents of this file are subject to the MRPL 1.2
 * * (the  "License"),  being   the  Mozilla   Public  License
 * * Version 1.1  with a permitted attribution clause; you may not  use this
 * * file except in compliance with the License. You  may  obtain  a copy of
 * * the License at http://www.floreantpos.org/license.html
 * * Software distributed under the License  is  distributed  on  an "AS IS"
 * * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * * License for the specific  language  governing  rights  and  limitations
 * * under the License.
 * * The Original Code is FLOREANT POS.
 * * The Initial Developer of the Original Code is OROCUBE LLC
 * * All portions are Copyright (C) 2015 OROCUBE LLC
 * * All Rights Reserved.
 * ************************************************************************
 */
package com.floreantpos.blockchyp;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.floreantpos.config.AppConfig;
import com.floreantpos.config.CardConfig;
import com.floreantpos.config.ui.ConfigurationView;
import com.floreantpos.swing.POSTextField;

import net.miginfocom.swing.MigLayout;

public class BlockChypConfigurationView extends ConfigurationView {

	private POSTextField gatewayUrlInput;
	private POSTextField apiIdInput;
	private POSTextField defaultTerminalInput;
	private POSTextField tfBearerToken;
	private POSTextField tfSigningKey;

	private String defaultHost = "https://api.blockchyp.com"; //$NON-NLS-1$

	public BlockChypConfigurationView() {
		setLayout(new MigLayout("", "[][grow]", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		
		JLabel lblGatewayUrl = new JLabel("Gateway URL"); //$NON-NLS-1$
		add(lblGatewayUrl, "cell 0 3,alignx leading"); //$NON-NLS-1$

		gatewayUrlInput = new POSTextField();
		add(gatewayUrlInput, "cell 1 3,growx"); //$NON-NLS-1$
	
		
        JLabel lblApiId = new JLabel("API Key"); //$NON-NLS-1$
        add(lblApiId, "cell 0 4,alignx leading"); //$NON-NLS-1$

        apiIdInput = new POSTextField();
        add(apiIdInput, "cell 1 4,growx"); //$NON-NLS-1$
        
        JLabel lblBearerToken = new JLabel("Bearer Token"); //$NON-NLS-1$
        add(lblBearerToken, "cell 0 5,alignx leading"); //$NON-NLS-1$

        tfBearerToken = new POSTextField();
        add(tfBearerToken, "cell 1 5,growx"); //$NON-NLS-1$
        
        JLabel lblSigningKey = new JLabel("Signing Key"); //$NON-NLS-1$
        add(lblSigningKey, "cell 0 6,alignx leading"); //$NON-NLS-1$

        tfSigningKey = new POSTextField();
        add(tfSigningKey, "cell 1 6,growx"); //$NON-NLS-1$
        
        JLabel lblDefaultTerminal = new JLabel("Default Terminal Name"); //$NON-NLS-1$
        add(lblDefaultTerminal, "cell 0 7,alignx leading"); //$NON-NLS-1$

        defaultTerminalInput = new POSTextField();
        add(defaultTerminalInput, "cell 1 7,growx"); //$NON-NLS-1$


	}


	@Override
	public void initialize() throws Exception {
	    
	    ObjectMapper mapper = new ObjectMapper();
	    
	    BlockChypConfiguration config = null;
	    
	    String base64 = AppConfig.getString("blockchyp-config");
	    
	    if (StringUtils.isNotEmpty(base64)) {
	    
        	    
        	    try {
        	        
        	        byte[] json = Base64.decodeBase64(base64);
        	        
        	        config = mapper.readValue(json, BlockChypConfiguration.class);
        	    } catch (Exception e) {
        	        e.printStackTrace();
        	    }
	    }
	    
	    
	    if (config == null) {
	        //check default creds
	        File aFile = new File("/etc/blockchyp/sdk-itest-config.json");
	        if (aFile.exists()) {
	            config = mapper.readValue(aFile, BlockChypConfiguration.class);
	        }
	        
	    }
	    
	    if (config == null) {
	        gatewayUrlInput.setText(defaultHost);
	    } else {
	        gatewayUrlInput.setText(config.getGatewayHost());
	        apiIdInput.setText(config.getApiId());
	        tfBearerToken.setText(config.getBearerToken());
	        tfSigningKey.setText(config.getSigningKey());
	        defaultTerminalInput.setText(config.getDefaultTerminalName());
	    }

	}


	@Override
	public boolean save() throws Exception {
	    
	    BlockChypConfiguration config = new BlockChypConfiguration();
	    
	    config.setGatewayHost(gatewayUrlInput.getText());
	    config.setApiId(apiIdInput.getText());
	    config.setBearerToken(tfBearerToken.getText());
	    config.setSigningKey(tfSigningKey.getText());
	    config.setDefaultTerminalName(defaultTerminalInput.getText());
	    
	    ObjectMapper mapper = new ObjectMapper();
	    byte[] json = mapper.writeValueAsBytes(config);

	    AppConfig.put("blockchyp-config", Base64.encodeBase64String(json));
	    
	    CardConfig.setExtTerminalSupported(true);
	    CardConfig.setManualEntrySupported(false);
	    CardConfig.setSwipeCardSupported(false);
	    
		return true;
	}

	@Override
	public String getName() {
		return ""; //$NON-NLS-1$
	}
	
}
