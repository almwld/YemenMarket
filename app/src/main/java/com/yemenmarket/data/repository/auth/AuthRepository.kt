package com.yemen.market.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.yemen.market.data.remote.model.User
import com.yemen.market.domain.model.UserType
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = getUserFromFirestore(authResult.user?.uid ?: "")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        email: String,
        password: String,
        name: String,
        phone: String,
        city: String,
        userType: UserType
    ): Result<User> {
        return try {
            // Create user in Firebase Auth
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            
            // Update user profile
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            authResult.user?.updateProfile(profileUpdates)?.await()

            // Create user document in Firestore
            val user = User(
                id = authResult.user?.uid ?: "",
                email = email,
                name = name,
                phone = phone,
                city = city,
                userType = userType,
                profileImage = "",
                createdAt = System.currentTimeMillis(),
                isActive = true
            )

            saveUserToFirestore(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun getCurrentUserType(): String? {
        val userId = getCurrentUserId() ?: return null
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            document.getString("userType")
        } catch (e: Exception) {
            null
        }
    }

    fun logout() {
        auth.signOut()
    }

    suspend fun getUserProfile(userId: String): User? {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun saveUserToFirestore(user: User): Boolean {
        return try {
            firestore.collection("users")
                .document(user.id)
                .set(user)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun getUserFromFirestore(userId: String): User {
        return firestore.collection("users")
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java) ?: throw Exception("User not found")
    }
}
