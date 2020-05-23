package it.univaq.examifire.util;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.User;

public class Utils {

	public static boolean hasAdminRole(User user) {
		return user.getRoles().stream().filter(role -> role.getName().equals(Role.ADMIN_ROLE_NAME)).findFirst().isPresent();
	}

	

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * {@code Utils} instances should NOT be constructed in
	 * standard programming. Instead, the class should be used statically.
	 * </p>
	 *
	 * <p>
	 * This constructor is public to permit tools that require a JavaBean instance
	 * to operate.
	 * </p>
	 */
	public Utils() {
		super();
	}
}
