import React, { Component } from "react";
import {withStyles} from "@material-ui/core/styles";

const styles = theme => ({
  submitBox: {
    width: '100%',
    height: '100%',
  }
});

class SubmitInput extends Component {
  
  constructor() {
    super();
    this.state = {
      textAreaValue: ""
    };
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event) {
    this.setState({ textAreaValue: event.target.value });
  }

  handleSubmit() {
    alert('A name was submitted: ' + this.state.textAreaValue);
  }

  render() {
    const { classes } = this.props;

    return (
      <div>
          <label>Enter value : </label>
          <textarea
            value={this.state.textAreaValue}
            onChange={this.handleChange}
            className={classes.submitBox}
          />

          <button onClick={() => this.handleSubmit()}>Submit</button>
      </div>
    );
  }
}

export default withStyles(styles) (SubmitInput);