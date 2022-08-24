import org.scalameter._
import sttp.client3.{Identity, Response, UriContext, basicRequest}
import sttp.client3.quick.backend
import scala.io.Source
import scala.util.Random

object Main extends App {
  def postRequest(zip: String): Unit = {
    val response = basicRequest.post(uri"https://connect.dev.werally.in/internal/provider/v1/search/unboosted")
      .body(
        s"""
           |{
           |    "autoIncreaseDistance": true,
           |    "configuration": "uhc",
           |    "from": 0,
           |    "size": 100,
           |    "includeAggregations": true,
           |    "showOnlineSchedulingAggregation": true,
           |    "showVirtualCareSpecialtyCategory": false,
           |    "zipCode": "$zip",
           |    "specialties": [
           |        "33"
           |    ],
           |    "searchTypes": [
           |        "person"
           |    ],
           |    "distanceMiles": 20,
           |    "networkParams": {
           |        "networksByDataSet": {
           |            "medical": [
           |                {
           |                    "network": "52",
           |                    "planCode": "52"
           |                }
           |            ]
           |        }
           |    }
           |}
      """.stripMargin)
      .contentType("Application/Json")
      .send(backend)
    println(s"response: $response.code.code")
  }

  val src = Source.fromFile("data/us_zip.csv")
  val data = src.getLines().drop(1).map(_.split(",").toList).toList
  val runs = 100

  val runTimes = (1 to runs)
    .map { x =>
      val index = Random.nextInt(data.length)
      val zip = data(index)(0)
      val time = measure { postRequest(zip) }

      println(time)
      println(s"$zip\ndone: $x\n")
      time.value
    }

  println(s"\nFor ${runs}")
  println(s"The avg time is: ${runTimes.sum.toDouble/runTimes.length}ms")
}