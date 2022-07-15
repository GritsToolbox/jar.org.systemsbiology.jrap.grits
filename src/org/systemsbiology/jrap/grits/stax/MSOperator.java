package org.systemsbiology.jrap.grits.stax;

/**
 * MSOperator provides information on who operated the hardware used to acquire
 * data.
 *
 * @author Mathijs Vogelzang
 */

public class MSOperator
{
	public String firstName, lastName;
	public String phoneNumber,
	email, URI;

    public String toString()
    {
	return ("firstName "+firstName+" lastName "+lastName
		+" phoneNumber "+phoneNumber+" email "+email
		+" URI "+URI);
    }
}
