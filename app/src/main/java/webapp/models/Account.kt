package webapp.models

//import jakarta.validation.constraints.Email
//import jakarta.validation.constraints.NotBlank
//import jakarta.validation.constraints.Pattern
//import jakarta.validation.constraints.Size
import java.time.Instant

/**
 * Représente l'account domain model sans le password
 */
//TODO: add field enabled=false
data class Account(
    val id: Long? = null,
//    @field:NotBlank
//    @field:Pattern(regexp = LOGIN_REGEX)
//    @field:Size(min = 1, max = 50)
    val login: String? = null,
//    @field:Size(max = 50)
    val firstName: String? = null,
//    @field:Size(max = 50)
    val lastName: String? = null,
//    @field:Email
//    @field:Size(min = 5, max = 254)
    val email: String? = null,
//    @field:Size(max = 256)
    val imageUrl: String? = null,
    val activated: Boolean = false,
//    @field:Size(min = 2, max = 10)
    val langKey: String? = null,
    val createdBy: String? = null,
    val createdDate: Instant? = null,
    val lastModifiedBy: String? = null,
    val lastModifiedDate: Instant? = null,
    val authorities: Set<String>? = null
) {
    fun isActivated() = activated
    val toAvatar get() = Avatar(id = id, login = login)
}
/*
   val toModel: Account
        get() = Account(
            id = id,
            login = login,
            firstName = firstName,
            lastName = lastName,
            email = email,
            imageUrl = imageUrl,
            activated = activated,
            langKey = langKey,
            createdBy = createdBy,
            createdDate = createdDate,
            lastModifiedBy = lastModifiedBy,
            lastModifiedDate = lastModifiedDate,
            authorities = authorities?.map { it.role }?.toSet()
        )

    val toCredentialsModel: AccountCredentials
        get() = AccountCredentials(
            id = id,
            login = login,
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            activationKey = activationKey,
            imageUrl = imageUrl,
            activated = activated,
            langKey = langKey,
            createdBy = createdBy,
            createdDate = createdDate,
            lastModifiedBy = lastModifiedBy,
            lastModifiedDate = lastModifiedDate,
            authorities = authorities?.map { it.role }?.toSet()
        )
*/