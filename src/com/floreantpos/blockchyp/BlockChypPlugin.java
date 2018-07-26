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

import java.awt.Component;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.floreantpos.config.ui.ConfigurationView;
import com.floreantpos.extension.PaymentGatewayPlugin;
import com.floreantpos.model.PosTransaction;
import com.floreantpos.model.Ticket;
import com.floreantpos.ui.views.payment.AuthorizeDotNetProcessor;
import com.floreantpos.ui.views.payment.CardProcessor;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class BlockChypPlugin extends PaymentGatewayPlugin {
	public static final String ID = "BlockChyp"; //$NON-NLS-1$

	protected BlockChypConfigurationView view;

	@Override
	public boolean requireLicense() {
		return false;
	}

	@Override
	public String getProductName() {
		return "BlockChyp"; //$NON-NLS-1$
	}

	@Override
	public ConfigurationView getConfigurationPane() throws Exception {
		if (view == null) {
			view = new BlockChypConfigurationView();
			view.initialize();
		}

		return view;
	}

	@Override
	public void initUI() {

	}

	@Override
	public void initBackoffice() {

	}

	@Override
	public void initConfigurationView(JDialog dialog) {

	}

	@Override
	public String toString() {
		return getProductName();
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String getSecurityCode() {
		return "-1105096101";
	}

	@Override
	public CardProcessor getProcessor() {
		return new BlockChypProcessor();
	}

	@Override
	public boolean shouldShowCardInputProcessor() {
		return true;
	}

	@Override
	public List<AbstractAction> getSpecialFunctionActions() {
		return null;
	}

	@Override
	public void initLicense() {
	}

	@Override
	public boolean hasValidLicense() {
		return true;
	}

	@Override
	public String getProductVersion() {
		return null;
	}

	@Override
	public Component getParent() {
		return null;
	}

	@Override
	public boolean printUsingThisTerminal() {
		return false;
	}

	@Override
	public void printTicket(Ticket ticket) {
	}

	@Override
	public void printTransaction(PosTransaction transaction, boolean storeCopy, boolean customerCopy) {
	}

	@Override
	public void printTicketWithTipsBlock(Ticket ticket) {
	}
}
