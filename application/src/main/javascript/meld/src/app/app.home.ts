import {Component} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  templateUrl: 'app.home.html',
})
export class AppHome {

  constructor(private http : HttpClient,
              private sanitizer: DomSanitizer) {
  }

  signIn() {

    //window.location.href = "service/channel/google/plus/signIn"

    function initMap() {
      // Create a map object and specify the DOM element for display.
      var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 8
      });

      var request = {
        location: map.getCenter(),
        radius: 500,
        query: 'Hamburg'
      };

      var service = new google.maps.places.PlacesService(map);
      service.textSearch(request, callback);

      function callback(results, status) {
        if (status == google.maps.places.PlacesServiceStatus.OK) {
          var marker = new google.maps.Marker({
            map: map,
            place: {
              placeId: results[0].place_id,
              location: results[0].geometry.location
            },
            position : results[0].lat
          });
        }
      }

    }

    initMap();

  }

}
