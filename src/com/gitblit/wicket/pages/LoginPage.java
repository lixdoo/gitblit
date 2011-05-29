/*
 * Copyright 2011 gitblit.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitblit.wicket.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.gitblit.Constants;
import com.gitblit.GitBlit;
import com.gitblit.Keys;
import com.gitblit.models.UserModel;
import com.gitblit.wicket.GitBlitWebSession;

public class LoginPage extends WebPage {

	IModel<String> username = new Model<String>("");
	IModel<String> password = new Model<String>("");

	public LoginPage(PageParameters params) {
		super(params);

		// If we are already logged in because user directly accessed
		// the login url, redirect to the home page
		if (GitBlitWebSession.get().isLoggedIn()) {
			setRedirect(true);
			setResponsePage(getApplication().getHomePage());
		}

		add(new Label("title", GitBlit.getString(Keys.web.siteName, Constants.NAME)));
		add(new Label("name", Constants.NAME));

		StatelessForm<Void> loginForm = new StatelessForm<Void>("loginForm") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				String username = LoginPage.this.username.getObject();
				char[] password = LoginPage.this.password.getObject().toCharArray();

				UserModel user = GitBlit.self().authenticate(username, password);
				if (user == null) {
					error("Invalid username or password!");
				} else {
					loginUser(user);
				}
			}
		};
		loginForm.add(new TextField<String>("username", username));
		loginForm.add(new PasswordTextField("password", password));
		loginForm.add(new FeedbackPanel("feedback"));
		add(loginForm);
	}

	private void loginUser(UserModel user) {
		if (user != null) {
			// Set the user into the session
			GitBlitWebSession.get().setUser(user);

			if (!continueToOriginalDestination()) {
				// Redirect to home page
				setResponsePage(getApplication().getHomePage());
			}
		}
	}
}
