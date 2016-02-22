/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;



public class Longitude extends GeographicCoordinateImpl {

   public static enum Direction {
      EAST( "E" ),
      WEST( "W" );

      private String abbreviation;

      private Direction( final String abbr ) {
         this.abbreviation = abbr;
      }

      public String getAbbreviation() {
         return abbreviation;
      }
   };

   private Direction direction;

   public static final int MAX_VALUE = 180;


   public Longitude() {
      super();
   }

   public Longitude( final double longitude ) throws GeographicCoordinateException {
      super( longitude );
      setDirection( longitude > 0.0d ? Direction.EAST : Direction.WEST );
   }

   public Longitude( final int degrees, final int minutes, final double seconds, final Longitude.Direction dir ) throws GeographicCoordinateException {
      super( degrees, minutes, seconds );
      setDirection( dir );
   }

   /**
    * @param direction Must be a member of {@code Longitude.Direction}
    */
   public void setDirection( final Longitude.Direction direction ) {
      if( direction == null )  throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      this.direction = direction;
   }

   public Longitude.Direction getDirection() {
      return direction;
   }

   @Override
   public double toDouble() {
      if( getDirection() == null )  throw new IllegalStateException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      final double decimal = getDegrees() + (getMinutes() / 60.0d) + (getSeconds() / 3600.0d);

      return getDirection() == Direction.EAST ? decimal : -decimal;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();

      result = prime * result + (direction == null ? 0 : direction.hashCode());

      return result;
   }

   @Override
   public boolean equals( final Object compareTo ) {
      final Longitude other;

      if( this == compareTo ) return true;

      if( !(compareTo instanceof Longitude) ) return false;

      other = (Longitude) compareTo;

      if( getDirection() == null && other.getDirection() != null ) return false;
      if( getDirection() != null && other.getDirection() == null ) return false;
      if( !getDirection().equals(other.getDirection()) ) return false;

      return super.equals( other );
   }

   /**
    * Returns a degree-minute-seconds formatted longitude.  For example:  30�40'50.123"E
    */
   @Override
   public String toString() {
      return String.format( Locale.US,
                            "%s%s",
                            super.toString(),
                            getDirection().getAbbreviation() );
   }
}
