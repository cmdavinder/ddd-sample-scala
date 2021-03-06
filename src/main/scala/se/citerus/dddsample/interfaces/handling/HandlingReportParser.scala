package se.citerus.dddsample.interfaces.handling

import com.aggregator.HandlingReport
import se.citerus.dddsample.domain.model.location.UnLocode
import se.citerus.dddsample.domain.model.handling.HandlingEventType
import java.util.Date
import scala.collection.mutable.ListBuffer
import se.citerus.dddsample.domain.model.voyage.VoyageNumber
import se.citerus.dddsample.domain.model.cargo.TrackingId
import java.text.SimpleDateFormat
import org.apache.commons.lang.StringUtils

object HandlingReportParser {
  val ISO_8601_FORMAT = "yyyy-MM-dd HH:mm"

  def parseUnLocode(unlocode: String, errors: ListBuffer[String]): UnLocode = {
    try {
      return new UnLocode(unlocode);
    } catch {
      case e => errors.append(e.getMessage());
    }
    return null;
  }

  def parseTrackingId(trackingId: String, errors: ListBuffer[String]): TrackingId = {
    try {
      return new TrackingId(trackingId);
    } catch {
      case e => errors.append(e.getMessage());
    }
    return null;
  }

  def parseVoyageNumber(voyageNumber: String, errors: ListBuffer[String]): VoyageNumber = {
    if (StringUtils.isNotEmpty(voyageNumber)) {
      return null;
    }

    try {
      return new VoyageNumber(voyageNumber);
    } catch {
      case e => errors.append(e.getMessage());
    }
    return null;
  }

  def parseDate(completionTime: String, errors: ListBuffer[String]): Date = {
    try {
      return new SimpleDateFormat(ISO_8601_FORMAT).parse(completionTime);
    } catch {
      case e => errors.append("Invalid date format: " + completionTime + ", must be on ISO 8601 format: " + ISO_8601_FORMAT);
    }
    return null;
  }

  def parseEventType(eventType: String, errors: ListBuffer[String]): HandlingEventType = {
    try {
      return HandlingEventType.valueOf(eventType);
    } catch {
      case e => errors.append(eventType + " is not a valid handling event type.")
    }
    return null;
  }

  def parseCompletionTime(handlingReport: HandlingReport, errors: ListBuffer[String]): Date = {
    val completionTime = handlingReport.completionTime;
    if (completionTime == null) {
      errors.append("Completion time is required");
      return null;
    }

    return completionTime.toGregorianCalendar().getTime();
  }
}
