package com.iamkamrul.ui.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.iamkamrul.domain.outcome.DataError
import com.iamkamrul.ui.R

@Composable
fun DataError.asUiText(): String = when (this) {
    DataError.Network.NoInternet -> stringResource(R.string.error_no_internet)
    DataError.Network.Timeout -> stringResource(R.string.error_timeout)
    DataError.Network.UnknownHost -> stringResource(R.string.error_unknown_host)
    DataError.Network.ConnectionShutdown -> stringResource(R.string.error_connection_shutdown)
    DataError.Network.SslHandshake -> stringResource(R.string.error_ssl)

    is DataError.Http.BadRequest -> stringResource(R.string.error_bad_request)
    is DataError.Http.Unauthorized -> stringResource(R.string.error_unauthorized)
    is DataError.Http.Forbidden -> stringResource(R.string.error_forbidden)
    is DataError.Http.NotFound -> stringResource(R.string.error_not_found)
    is DataError.Http.Conflict -> stringResource(R.string.error_conflict)
    is DataError.Http.TooManyRequests -> stringResource(R.string.error_rate_limit)
    is DataError.Http.InternalServerError -> stringResource(R.string.error_server, code)
    is DataError.Http.Unknown -> stringResource(R.string.error_unknown, code)

    DataError.Local.ParseError -> stringResource(R.string.error_parsing)
    DataError.Local.EmptyResponse -> stringResource(R.string.error_empty_response)
    DataError.Local.DiskFull -> stringResource(R.string.error_disk_full)
    DataError.Local.Unknown -> stringResource(R.string.error_unknown)

    is DataError.ServerMessage -> message  // already localized by backend
}
