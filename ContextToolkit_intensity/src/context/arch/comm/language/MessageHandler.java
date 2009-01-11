package context.arch.comm.language;

import context.arch.comm.DataObject;
import context.arch.MethodException;
import context.arch.InvalidMethodException;

import java.io.Reader;

/**
 * This interface specifies all the methods for encoding outgoing messages
 * and decoding incoming messages, as well as the method that handles
 * decoded incoming messages.
 */
public interface MessageHandler {
  
  /** 
   * Method to decode the incoming data
   *
   * @param message Message to be decoded
   * @return the decoded message in a DataObject
   * @exception context.arch.comm.language.DecodeException thrown when the given
   *		data can not be decoded successfully
   * @exception context.arch.comm.language.InvalidDecoderException thrown when the 
   *		decoder can not be run successfully
   */
  public abstract DataObject decodeData(Reader message) throws DecodeException, InvalidDecoderException;

  /** 
   * Method to encode the incoming data
   *
   * @param data Data to be encoded
   * @return the encoded message
   * @exception context.arch.comm.language.EncodeException thrown when the given
   *		data can not be encoded successfully
   * @exception context.arch.comm.language.InvalidEncoderException thrown when the
   *		encoder can not be run successfully
   */
  public abstract String encodeData(DataObject data) throws EncodeException, InvalidEncoderException;

  /**
   * This method handles both the system-defined, callbacks and user-defined RPCs.  
   *
   * @param methodType Name of method to run
   * @param data DataObject containing data for the method call
   * @exception context.arch.InvalidMethodException thrown if specified RPC couldn't be found
   * @exception context.arch.MethodException thrown if specified RPC had an error
   */
  public abstract DataObject runMethod(String methodType, DataObject data) throws InvalidMethodException, MethodException;

}
