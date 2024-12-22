package com.vishvendra.journeylens.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

  private byte[] cachedBody;

  public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
    super(request);
    InputStream requestInputStream = request.getInputStream();
    this.cachedBody = requestInputStream.readAllBytes();
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
    return new CachedServletInputStream(byteArrayInputStream);
  }

  public byte[] getCachedBody() {
    return cachedBody;
  }

  private static class CachedServletInputStream extends ServletInputStream {

    private final InputStream inputStream;

    public CachedServletInputStream(InputStream inputStream) {
      this.inputStream = inputStream;
    }

    @Override
    public boolean isFinished() {
      try {
        return inputStream.available() == 0;
      } catch (IOException e) {
        return true;
      }
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
      throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
      return inputStream.read();
    }
  }
}
