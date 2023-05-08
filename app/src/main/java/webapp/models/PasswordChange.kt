package webapp.models

/*=================================================================================*/
data class PasswordChange(
    val currentPassword: String? = null,
    val newPassword: String? = null,
)
/*
PasswordChangeDTO{
    currentPassword	string
    newPassword	string
}
*/