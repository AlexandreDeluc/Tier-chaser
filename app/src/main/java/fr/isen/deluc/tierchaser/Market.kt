package fr.isen.deluc.tierchaser

import java.io.Serializable

class Market (var name:String, var avatar:String, var latitude:Double, var longitude:Double):Serializable{
    constructor():this("", "",0.0, 0.0)
}