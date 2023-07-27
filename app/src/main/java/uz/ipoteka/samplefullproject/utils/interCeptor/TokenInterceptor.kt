package uz.ipoteka.samplefullproject.utils.interCeptor

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpHeaders
import okhttp3.*
import uz.ipoteka.samplefullproject.utils.constant.AppConstant.APPLICATION_JSON
import uz.ipoteka.samplefullproject.utils.localeStorage.LocalStorage
import java.net.HttpURLConnection
import javax.inject.Inject


class TokenInterceptor @Inject constructor(
    private val localeStorage: LocalStorage
) : Interceptor {
    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest: Request = newRequestWithAccessToken(chain.request(), localeStorage.token)
        var oldResponse = chain.proceed(oldRequest)
        if (oldResponse.code == HttpURLConnection.HTTP_UNAUTHORIZED){
            localeStorage.token = ""
        }
        return oldResponse
    }


    private fun newRequestWithAccessToken(request: Request,accessToken: String): Request {
        return request.newBuilder()
//            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .header(HttpHeaders.ACCEPT,APPLICATION_JSON)
            .header(HttpHeaders.CONTENT_TYPE,APPLICATION_JSON)
            .build()
    }
}
