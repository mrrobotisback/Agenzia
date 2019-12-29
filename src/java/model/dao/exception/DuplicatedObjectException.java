/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.exception;

/**
 *
 * @author Mr. robot
 */
//@SuppressWarnings("serial")
public class DuplicatedObjectException extends Exception {

  private static final long serialVersionUID = 999;

  /**
   * Creates a new instance of
   * <code>DuplicatedObjectException</code> without detail message.
   */
  public DuplicatedObjectException() {
  }

  /**
   * Constructs an instance of
   * <code>DuplicatedObjectException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public DuplicatedObjectException(String msg) {
    super(msg);
  }
}
