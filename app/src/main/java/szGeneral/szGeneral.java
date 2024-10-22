package szGeneral;

public class szGeneral {
    // returns error message string, or "" if success
    public static String ValidateInput(String _szEmail, String _szPassword)
    {
        String NOERROR = "";
        String ERROR_EMAIL_FORMAT = "Email must follow format: x@x";
        String ERROR_PASSWORD_SHORT = "Password must be at least 6 characters long";

        //enableSubmitIfReady();
        //boolean isReady = yourEditText.getText().toString().length() > 3;
        //yourbutton.setEnabled(isReady);
        //m_btnRegister.setEnabled(m_txtPassword.getText().toString().length() >= 6);
        //m_btnRegister.setHint("Password must be at least 6 characters long");

        // email format valid (3 chars with @ not as first or last char - catches everything!)
        int nLength = _szEmail.length();
        if (nLength < 3)
        {
            return ERROR_EMAIL_FORMAT;
        }
        if (_szEmail.charAt(0) == '@' || _szEmail.charAt(nLength - 1) == '@')
        {
            return ERROR_EMAIL_FORMAT; // no errors
        }

        // password too short (catches empty!)
        if (_szPassword.length() < 6)
        {
            return ERROR_PASSWORD_SHORT;
        }

        return NOERROR;
    }
}
