package com.example.jetfitnessapp.data.remote

import android.util.Log
import com.example.jetfitnessapp.data.remote.models.AuthRequest
import com.example.jetfitnessapp.data.remote.models.AuthResponse
import com.example.jetfitnessapp.data.remote.models.Progress
import com.example.jetfitnessapp.data.remote.models.Workout
import com.example.jetfitnessapp.data.remote.models.WorkoutNameAndWorkout
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import java.io.IOException

class ApiService (

    private val client: HttpClient

) {

    private val baseUrl: String = "http://192.168.0.6:8080"


    suspend fun serverCheck () : String {

        val response: HttpResponse = client.get(baseUrl)

        return  response.body<String>()

    }

    suspend fun login ( email: String , password: String ) : String {

        val response: HttpResponse = client.post( "$baseUrl/login" ) {

            contentType( ContentType.Application.Json )
            setBody( AuthRequest ( email , password ) )

        }

        return if ( response.status == HttpStatusCode.OK ) {

            response.body< AuthResponse >().token

        } else {

            Log.d( "Login Error" , "${response.status}" )
            "Error in Login"

        }

    }

    suspend fun signup ( email: String , password: String ) : String {

        val response: HttpResponse = client.post( "$baseUrl/signup" ) {

            contentType( ContentType.Application.Json )
            setBody( AuthRequest ( email , password ) )

        }

        return if ( response.status == HttpStatusCode.OK ) {

            response.body< AuthResponse >().token

        } else {

            Log.d( "Signup Error" , "${response.status}" )
            "Error in Signup"

        }

    }

    suspend fun isAuthenticated ( token: String ) : Boolean {

        return try {

            val response: HttpResponse = client.get( "$baseUrl/authenticate" ) {

                headers {

                    append( HttpHeaders.Authorization , "Bearer $token" )

                }

            }

            response.status == HttpStatusCode.OK

        } catch ( e: Exception ) {

            Log.d( "Is Authenticated Error" , "$e" )
            false

        }

    }

    suspend fun getProgress ( token: String ) : List<Progress?> {

        val response: HttpResponse = client.get( "$baseUrl/getProgress" ) {

            headers {

                append( HttpHeaders.Authorization , "Bearer $token" )

            }

        }

        return if ( response.status == HttpStatusCode.OK ) {

            response.body< List<Progress?> >()

        } else {

            emptyList()

        }

    }

    suspend fun getWeight ( token: String ) : List<Int> {

        val response: HttpResponse = client.get( "$baseUrl/getWeight" ) {

            headers {

                append( HttpHeaders.Authorization , "Bearer $token" )

            }

        }

        return if ( response.status == HttpStatusCode.OK ) {

            response.body< List<Int> >()

        } else {

            emptyList()

        }

    }

    suspend fun getWorkouts ( token: String ) : List< WorkoutNameAndWorkout > {

        val response: HttpResponse = client.get( "$baseUrl/getWorkouts" ){

            headers {

                append( HttpHeaders.Authorization , "Bearer $token" )

            }

        }

        return if ( response.status == HttpStatusCode.OK ) {

            response.body< List< WorkoutNameAndWorkout > >()

        } else {

            emptyList()

        }

    }

    suspend fun postProgress(token: String, progress: Progress): String {

        return try {

            val response: HttpResponse = client.post("$baseUrl/addProgress") {

                headers {

                    append(HttpHeaders.Authorization, "Bearer $token")
                    contentType(ContentType.Application.Json)

                }

                setBody(progress) // Fixing incorrect usage of `setBody`

            }

            if (response.status == HttpStatusCode.OK) {

                response.body<String>() // Accessing response body

            } else {

                "Error: ${response.status} - ${response.body<String>()}" // Getting error message from server

            }

        } catch (e: Exception) {

            "Exception: ${e.localizedMessage}" // Handling network exceptions

        }

    }

    suspend fun getDate ( token: String ) : List< String > {

        val response: HttpResponse = client.get( "$baseUrl/getDate" ) {

            headers {

                append( HttpHeaders.Authorization , "Bearer $token" )

            }

        }

        return if ( response.status == HttpStatusCode.OK ) {

            response.body< List<String> >()

        } else {

            emptyList()

        }

    }

}