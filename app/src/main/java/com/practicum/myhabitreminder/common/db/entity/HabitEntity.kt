package com.practicum.myhabitreminder.common.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practicum.myhabitreminder.common.db.entity.HabitEntity.CREATOR.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val description: String?,
    val timeStamp: String?,
    val daysCounter: Int?
): Parcelable

{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HabitEntity> {
        override fun createFromParcel(parcel: Parcel): HabitEntity {
            return HabitEntity(parcel)
        }

        override fun newArray(size: Int): Array<HabitEntity?> {
            return arrayOfNulls(size)
        }

        const val TABLE_NAME = "habit_table"
    }
}