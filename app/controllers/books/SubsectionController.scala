package controllers.books

import domain.books.Subsection
import domain.security.TokenFailExcerption
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.books.SubsectionService

import scala.concurrent.ExecutionContext.Implicits.global

class SubsectionController@Inject()
(cc: ControllerComponents) extends AbstractController(cc)  {

  def create: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Subsection](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- SubsectionService.apply.saveEntity(entity)
      } yield result
      response.map(ans => Ok(Json.toJson(entity)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def update: Action[JsValue] = create

  def getEntity(id: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
      //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- SubsectionService.apply.getEntity(id)
      } yield result
      response.map(ans => Ok(Json.toJson(ans)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def delete: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Subsection](input).get
      val response = for {
      //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- SubsectionService.apply.deleteEntity(entity)
      } yield result
      response.map(ans => Ok(Json.toJson(ans)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getSectionSubsections(sectionId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- SubsectionService.apply.getSectionSubsections(sectionId)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
