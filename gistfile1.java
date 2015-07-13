public class GeoLocationUtils {

	private static final double EARTH_RADIUS = 6371000;
	private static final double EARTH_CIRCLE_LENGTH = 2 * Math.PI * EARTH_RADIUS;
	private static final double DISTANCE_PER_EARTH_CIRCLE_DEGREE = Math.PI * EARTH_RADIUS / 180;

	/**
	 * @see <a href="http://en.wikipedia.org/wiki/Great-circle_distance">Great-circle distance</a>
	 */
	public static double getDistance(double latitude0, double longitude0, double latitude1, double longitude1) {
		final double latitude0Rad = Math.toRadians(latitude0);
		final double longitude0Rad = Math.toRadians(longitude0);
		final double latitude1Rad = Math.toRadians(latitude1);
		final double longitude1Rad = Math.toRadians(longitude1);
		final double deltaLongitudeRad = longitude1Rad - longitude0Rad;

		return EARTH_RADIUS * Math.atan2(Math.sqrt(Math.pow(Math.cos(latitude1Rad) * Math.sin(deltaLongitudeRad), 2)
				+ Math.pow(Math.cos(latitude0Rad) * Math.sin(latitude1Rad) - Math.sin(latitude0Rad) * Math.cos(latitude1Rad)
				* Math.cos(deltaLongitudeRad), 2)), Math.sin(latitude0Rad) * Math.sin(latitude1Rad) + Math.cos(latitude0Rad)
				* Math.cos(latitude1Rad) * Math.cos(deltaLongitudeRad));
	}

	public static double getLatitude(double latitude, double deltaLongitudeMeters) {
		latitude = (latitude + deltaLongitudeMeters / DISTANCE_PER_EARTH_CIRCLE_DEGREE) % 360;
		return latitude >= 270 ? -360 + latitude : latitude > 90 ? 180 - latitude :
			   latitude <= -270 ? 360 + latitude : latitude < -90 ? -180 - latitude : latitude;
	}

	public static double getLongitude(double latitude, double longitude, double deltaLatitudeMeters) {
		final double earthCircleLength = EARTH_CIRCLE_LENGTH * Math.cos(Math.toRadians(Math.abs(latitude)));
		final double distancePerDegree = earthCircleLength / 360;
		longitude = (longitude + deltaLatitudeMeters / distancePerDegree) % 360;
		return longitude > 180 ? -360 + longitude : longitude < -180 ? 360 + longitude : longitude == -180 ? 180 : longitude;
	}
}