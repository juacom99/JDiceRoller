/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.improvisados.jdiceroller;

/**
 *
 * @author Joaquin Martinez <juacom04@gmail.com>
 */
public class InvalidRollException extends Exception
{

    public InvalidRollException()
    {
        super("This is an invalid roll");
    }
    
    public InvalidRollException(String message)
    {
        super(message);
    }
        
}
