/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 *
 *  http://www.goorulearning.org/
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.application.shared.model.user;

import com.google.gwt.user.client.rpc.IsSerializable; 
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonFilter("userRole")
@JsonInclude(Include.NON_NULL)
public class UserRoleDo implements IsSerializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2207392878844326301L;
	/**
	 * Serial Version
	 */

	private Short roleId;
	private String name;
	private String description;
	private Set<RoleEntityOperationDo> roleOperations;

	public static final Short ROLE_TEACHER = 1;
	public static final Short ROLE_STUDENT = 2;
	public static final Short ROLE_CONTENT_ADMIN = 3;
	public static final Short ROLE_ANONYMOUS = 4;
	public static final Short ROLE_AUTHENTICATED = 5;
	public static final Short ROLE_PUBLISHER = 6;

	public static enum UserRoleType {
		TEACHER("Teacher"), STUDENT("Student"), CONTENT_ADMIN("Content_Admin"), ANONYMOUS("ANONYMOUS"), AUTHENTICATED_USER("User"), PUBLISHER("Publisher");

		private String type;

		UserRoleType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

	}

	public UserRoleDo() {
		this.roleOperations = new HashSet<RoleEntityOperationDo>();
	}

	public Short getRoleId() {
		return roleId;
	}

	public void setRoleId(Short roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<RoleEntityOperationDo> getRoleOperations() {
		return roleOperations;
	}

	public void setRoleOperations(Set<RoleEntityOperationDo> roleOperations) {
		this.roleOperations = roleOperations;
	}
}