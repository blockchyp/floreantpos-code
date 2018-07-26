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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.blockchyp.client.BlockChypClient;
import com.blockchyp.client.GatewayCredentials;
import com.blockchyp.client.dto.ChargeRequest;
import com.blockchyp.client.dto.ChargeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.floreantpos.config.AppConfig;
import com.floreantpos.model.PosTransaction;
import com.floreantpos.ui.views.payment.CardProcessor;

public class BlockChypProcessor implements CardProcessor {

    @Override
    public void preAuth(PosTransaction transaction) throws Exception {
        System.out.println("Your Mom");
        
    }

    @Override
    public void captureAuthAmount(PosTransaction transaction) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void chargeAmount(PosTransaction transaction) throws Exception {
        
        BlockChypConfiguration config = getConfiguration();
        
        GatewayCredentials creds = new GatewayCredentials();
        creds.setApiId(config.getApiId());
        creds.setBearerToken(config.getBearerToken());
        creds.setSigningKey(config.getSigningKey());
        BlockChypClient client = new BlockChypClient(config.getGatewayHost(), creds);
        
        ChargeRequest request = new ChargeRequest();
        
        NumberFormat fmt = new DecimalFormat("#0.00");
        
        request.setAmount(fmt.format(transaction.calculateAuthorizeAmount()));
        ChargeResponse response = client.charge(config.getDefaultTerminalName(), request);

        if (response.isApproved()) {
            transaction.setCaptured(true);
        } 
        
    }
    
    protected BlockChypConfiguration getConfiguration() {
        
        String base64 = AppConfig.getString("blockchyp-config");
        
        ObjectMapper mapper = new ObjectMapper();

        try {
            byte[] json = Base64.decodeBase64(base64);
            return  mapper.readValue(json, BlockChypConfiguration.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        
    }

    @Override
    public void voidTransaction(PosTransaction transaction) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getCardInformationForReceipt(PosTransaction transaction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void cancelTransaction() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean supportTipsAdjustMent() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void adjustTips(PosTransaction transaction) throws Exception {
        // TODO Auto-generated method stub
        
    }
	
}
