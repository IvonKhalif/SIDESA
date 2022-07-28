package com.gov.sidesa.utils.extension

import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


fun <T : Any, R : Any, M : Any> NetworkResponse<T, R>.asDomain(
    transform: T.() -> M
) = when (this) {
    is NetworkResponse.Success -> NetworkResponse.Success(transform.invoke(body))
    is NetworkResponse.NetworkError -> NetworkResponse.NetworkError(error)
    is NetworkResponse.ServerError -> NetworkResponse.ServerError(body, code, headers)
}