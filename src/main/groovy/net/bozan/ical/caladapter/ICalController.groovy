package net.bozan.ical.caladapter

import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.property.CalScale
import net.fortuna.ical4j.model.property.Method
import net.fortuna.ical4j.model.property.ProdId
import net.fortuna.ical4j.model.property.Version
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse

@RestController
class ICalController {

  @Autowired
  ICalClient client

  @RequestMapping(value = '/4outlook/{id}.ical', method = RequestMethod.GET)
  void getOutlookICal(@PathVariable String id, HttpServletResponse response) {
    Calendar calendar = new Calendar()
    calendar.properties.add(new ProdId("-//Bozan//cal-adapter-0.0.1//EN"))
    calendar.properties.add(Version.VERSION_2_0)
    calendar.properties.add(CalScale.GREGORIAN)
    calendar.properties.add(Method.PUBLISH)

    client.readCalendar(id).components.each {
      calendar.components.add(it)
    }

    CalendarOutputter outputter = new CalendarOutputter()
    outputter.output(calendar, response.getOutputStream())
  }

}
