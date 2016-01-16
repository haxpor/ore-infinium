package com.ore.infinium.components

import com.artemis.Component
import com.ore.infinium.Inventory
import com.ore.infinium.LoadedViewport
import com.ore.infinium.OreTimer
import com.ore.infinium.OreWorld

/**
 * ***************************************************************************
 * Copyright (C) 2014 by Shaun Reich @gmail.com>                    *
 * *
 * This program is free software; you can redistribute it and/or            *
 * modify it under the terms of the GNU General Public License as           *
 * published by the Free Software Foundation; either version 2 of           *
 * the License, or (at your option) any later version.                      *
 * *
 * This program is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of           *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            *
 * GNU General Public License for more details.                             *
 * *
 * You should have received a copy of the GNU General Public License        *
 * along with this program.  If not, see //www.gnu.org/licenses/>.    *
 * ***************************************************************************
 */
class PlayerComponent : Component() {

    var playerName: String = ""

    /**
     * Unique and utilized only by players, is not global or related to generic entity id's
     * Is used to identify the players, for knowing which one the network is talking about,
     * and is also very useful for kicking/banning.
     */
    var connectionPlayerId = -1
    var killed: Boolean = false
    var placeableItemTimer = OreTimer()

    //    public Vector2 mousePositionWorldCoords;
    //    public boolean mouseLeftButtonHeld;
    //    public boolean mouseRightButtonHeld;
    @Transient var ping: Int = 0
    @Transient var noClip: Boolean = false

    var loadedViewport = LoadedViewport()
    var hotbarInventory: Inventory? = null
    var inventory: Inventory? = null
    //public int equippedItemAnimator;

    /**
     * @return entity id that is equipped as primary
     */
    val equippedPrimaryItem: Int?
        get() = hotbarInventory!!.itemEntity(hotbarInventory!!.selectedSlot)

    /**
     * note, has no copyFrom method, as it is should never be copied
     */

    override fun toString(): String {
        val c = javaClass.name
        return """
        $c.playerName: $playerName
        $c.connectionPlayerId: $connectionPlayerId
        $c.killed: $killed
        """
    }

    companion object {
        val jumpVelocity = OreWorld.GRAVITY_ACCEL * 18
        val movementSpeed = 3.5f
        val maxMovementSpeed = movementSpeed * 1.2f

        //ms
        val placeableItemDelay = 300
    }
}
