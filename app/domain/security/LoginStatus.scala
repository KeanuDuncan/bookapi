package domain.security

import play.api.libs.json.Json

case class LoginStatus(status:String="")

object LoginStatus{

  implicit val logInStatusFmt = Json.format[LoginStatus]
}
