import React, { Component } from "react";
import {withStyles} from "@material-ui/core/styles";
import { MDBInput } from "mdbreact";
import ReactTextareaAutocomplete from "@webscopeio/react-textarea-autocomplete";
import './App.css'

const styles = theme => ({
  submitBox: {
    fontSize: '20px',
    padding: '5px',
    border: '2px solid black',
  },
  
  label: {
    left: 0,
  },

  popup:{
    position: 'relative',
  },

  rta:{
    position: 'relative',
  }
});

class SubmitInput extends Component {
  
  constructor() {
    super();
    this.state = {
      textAreaValue: "",
      values: [],
      csv: "",
    };
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event) {
    this.setState({ textAreaValue: event.target.value });
  }

  handleTextChange = (event) => {
    this.setState({
      ...this.state,
      csv: event.target.files[0]
    })
  }

  handleSendFile() {
    let formData = new FormData();
    formData.append('file', this.state.csv);

    const requestOptions = {
      mode: 'no-cors',
      method: 'POST',
      body: formData,
    };

    console.log(this.state.csv);

    fetch('http://localhost:8080/test_FileUpload', requestOptions)
    .then((response) => response.json())
    .then((result) => {
      console.log('Success:', result);
    })
    .catch((error) => {
      console.error('Error:', error);
    });

    // fetch("http://localhost:8080/test_FileUpload", requestOptions).then(function (res) {
    //   if (res.ok) {
    //     alert("HELLO TAHER");
    //     return res.json();
    //   } else {
    //     return Promise.reject(res);
    //   }
    // }).then(function (response) {
    //   console.log("HELLO TAHER");
    // }).catch(function (error) {
    //   //Handle error
    //   console.log(error);
    // });
  }

  async handleSubmit() {
    
    // let arr = this.state.textAreaValue.split("\n");

    // for await(const text of arr){
    //   if(text.includes("maximize")){
    //     setTimeout(() => {
    //       console.log(text);

    //       // POST request using fetch with async/await
    //       const requestOptions = {
    //         method: 'POST',
    //         headers: { 'Content-Type': 'application/json' },
    //         body: this.state.textAreaValue,
    //       };

    //       // Fetch another API// POST request using fetch with async/await
    //       const requestOptions2 = {
    //         method: 'POST',
    //         headers: { 'Content-Type': 'application/json' },
    //         body: text.trim(),
    //       };

    //       this.handlePosts(requestOptions, requestOptions2);
    //     }, 3000);
          
    //   };
    // }

    // POST request using fetch with async/await
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: this.state.textAreaValue,
    };

    fetch('http://localhost:8080/test_GLPK', requestOptions).then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                return Promise.reject(response);
            }
        }).then(result => {
            console.log("Wrote to file " + result.log);
        });

    alert('Text was submitted: \n' + this.state.textAreaValue);
  }

  render() {
    const { classes } = this.props;
    const Item = ({ entity: { name, char } }) => <div style={{font: '20px', color: 'purple'}}>{`${char}`}</div>;

    return (
      <div className="container">
          <label className={classes.label}>Enter value : </label>
          <div>
            {/* <MDBInput
              type="textarea"
              label="Icon Prefix"
              rows="2"
              icon="pencil-alt"
              value={this.state.textAreaValue}
              onChange={this.handleChange}
              className={classes.submitBox}
            /> */}

            <div className="container">
              <ReactTextareaAutocomplete
                className={classes.submitBox}
                onChange={this.handleChange}
                loadingComponent={() => <span>Loading</span>}
                style={{position: 'relative', minWidth: '100%', minHeight: '300px', listStyleType: 'none',}}
                dropdownStyle={{ position: 'absolute', listStyleType: 'none', listStyleType: 'none' }}
                listStyle={{ listStyleType: 'none' }}
                renderToBody={true}
                itemStyle={{fontSize: '20px'}}
                trigger={{
                  "v": {
                    dataProvider: token => {
                      return [
                        { name: "var: ", char: "var" },
                      ];
                    },
                    component: Item,
                    output: (item) => item.name
                  },

                  "ma": {
                    dataProvider: token => {
                      return [
                        { name: "maximize: ", char: "maximize" },
                      ];
                    },
                    component: Item,
                    output: (item) => item.name
                  },

                  "su": {
                    dataProvider: token => {
                      return [
                        { name: "subject to: ", char: "subject to" },
                      ];
                    },
                    component: Item,
                    output: (item) => item.name
                  },

                  "st": {
                    dataProvider: token => {
                      return [
                        { name: "step: ", char: "step" },
                      ];
                    },
                    component: Item,
                    output: (item) => item.name
                  },
                }}
              />
            </div>

            <button onClick={() => this.handleSubmit()}>Solve</button>
          </div>

          <form action="..." method="post" encType="multipart/form-data">

            <input
              type="file"
              name="file"
              ref={(input) => { this.filesInput = input }}
              name="file"
              icon='file text outline'
              iconposition='left'
              label='Upload CSV'
              labelposition='right'
              placeholder='UploadCSV...'
              onChange={this.handleTextChange}
            />
          </form>

          <button onClick={() => this.handleSendFile()}>
            Send
          </button>
      </div>
    );
  }
}

export default withStyles(styles) (SubmitInput);