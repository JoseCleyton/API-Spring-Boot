package com.aulas.devdojo.awesome.error;

public class ResourceNotFoundDetails extends ErrorDetails {

	public static final class Builder {
		private String title;
		private int status;
		private String detail;
		private long timestamp;
		private String developerMessage;

		private Builder() {

		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withDetail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder withStatus(int status) {
			this.status = status;
			return this;
		}

		public Builder withDeveloperMessage(String developerMessage) {
			this.developerMessage = developerMessage;
			return this;
		}

		public Builder withTimestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public ResourceNotFoundDetails build() {
			ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
			resourceNotFoundDetails.setDeveloperMessage(developerMessage);
			resourceNotFoundDetails.setTitle(title);
			resourceNotFoundDetails.setDetail(detail);
			resourceNotFoundDetails.setTimestamp(timestamp);
			resourceNotFoundDetails.setStatus(status);
			return resourceNotFoundDetails;
		}
	}

}
