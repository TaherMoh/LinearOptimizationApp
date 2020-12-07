import React, { Component } from "react";
import {withStyles} from "@material-ui/core/styles";
import { MDBInput } from "mdbreact";
import ReactTextareaAutocomplete from "@webscopeio/react-textarea-autocomplete";
import './App.css'
import { Checkmark } from 'react-checkmark'
import { Typography } from "@material-ui/core";
import { CsvToHtmlTable } from 'react-csv-to-table';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import TextField from '@material-ui/core/TextField';

const styles = theme => ({
  submitBox: {
    fontSize: '20px',
    padding: '5px',
    border: '2px solid black',
  },
  
  label: {
    left: 0,
  },

  formControl: {
    minWidth: '33%',
    paddingRight: '10%',
  },

  completedTasksContainer: {
    float: 'right',
    width: '70%',
    padding: '5%',
    paddingTop: '10px',
    paddingBottom: '10px',
  },

  completedTasks: {
    color: 'white',
    backgroundColor: 'green',
    float: 'left',
    width: '90%',
  },

  popup:{
    position: 'relative',
  },

  rta:{
    position: 'relative',
  },

  floatContainer: {
    maxHeight: '100%',
  },

  floatChild: {
      width: '49%',
      float: 'left',
      padding: '15px',
  },

  floatChildLeft: {
      width: '49%',
      float: 'left',
      alignContent: 'center',
  },
});

class SubmitInput extends Component {
  
  constructor() {
    super();
    this.state = {
      textAreaValue: "",
      values: [],
      csv: "",
      initialized: false,
      uploaded: false,
      generated: false,
      solved: false,
      csv: "",
      numCol: 0,
      startCol: 0,
      endCol: 0,
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
      method: 'POST',
      body: formData,
    };

    console.log(this.state.csv);

    // fetch('http://localhost:8080/test_FileUpload', requestOptions)
    fetch('http://localhost:8080/initialize_Params', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        numCol: this.state.numCol,
        startCol: this.state.startCol,
        endCol: this.state.endCol,
      }),
    })
    .then(function (response) {
      if (response.ok) {
          return response.json();
      } else {
          return Promise.reject(response);
      }
    })
    .then((result) => {
      console.log('Initialized successfully:', result);
      this.setState({
        ...this.state,
        initialized: true,
      })
    })
    .then(function () {
      // Fetch another API
      return fetch('http://localhost:8080/test_FileUpload', requestOptions);
    })
    .then(function (response) {
      if (response.ok) {
          return response.json();
      } else {
          return Promise.reject(response);
      }
    })
    .then((result) => {
      console.log('Success:', result);
      this.setState({
        ...this.state,
        uploaded: true,
      })
    })
    .then(function () {
      // Fetch another API
      return fetch('http://localhost:8080/run_Optimizer', requestOptions);
    })
    .then(function (response) {
        if (response.ok) {
            return response.json();
        } else {
            return Promise.reject(response);
        }
    })
    .then(result => {
        console.log("Created output file? " + result);

        this.setState({
            ...this.state,
            generated: true,
        })
    })
    .then(function () {
      // Fetch another API
      return fetch('http://localhost:8080/get_CSV');
    })
    .then(function (response) {
        if (response.ok) {
            return response.json();
        } else {
            return Promise.reject(response);
        }
    })
    .then(result => {
        console.log("Received output data from server " + result.content);

        this.setState({
            ...this.state,
            solved: true,
            csv: result.content,
        })
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

  handleNumCol (event) {
    this.setState({
      ...this.state,
      numCol: event.target.value,
    })
  };

  handleStartCol (event) {
    this.setState({
      ...this.state,
      startCol: event.target.value,
    })
  };
  
  handleEndCol (event) {
    this.setState({
      ...this.state,
      endCol: event.target.value,
    })
  };
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
    // const Item = ({ entity: { name, char } }) => <div style={{font: '20px', color: 'purple'}}>{`${char}`}</div>;

    const Items = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15'];
    const MappingItems = Items.map((Item) => <MenuItem value={Item}>Item</MenuItem> );

    return (
      <div className={classes.floatContainer}>
        

        <div className={classes.floatChildLeft}>
                    {/* <label className={classes.label}>Enter value : </label> */}
            {/* <MDBInput
              type="textarea"
              label="Icon Prefix"
              rows="2"
              icon="pencil-alt"
              value={this.state.textAreaValue}
              onChange={this.handleChange}
              className={classes.submitBox}
            /> */}

              {/* <ReactTextareaAutocomplete
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
              /> */}
            

            {/* <button onClick={() => this.handleSubmit()}>Solve</button> */}

              <FormControl required className={classes.formControl}>
                  <InputLabel id="total-num-col">Number of columns</InputLabel>
                  <Select
                    labelId="total-num-col"
                    id="total-num-col-required"
                    value={this.state.numCol}
                    onChange={(event) => this.handleNumCol(event)}
                  >
                    <MenuItem value="">
                      <em>None</em>
                    </MenuItem>
                    {Items.map((Item) => <MenuItem value={Item}>{Item}</MenuItem> )}
                  </Select>
                  <FormHelperText>Required</FormHelperText>
              </FormControl>

              <FormControl required className={classes.formControl}>
                  <InputLabel id="col-start-index">Starting index</InputLabel>
                  <Select
                    labelId="col-start-index"
                    id="col-start-index-required"
                    value={this.state.startCol}
                    onChange={(event) => this.handleStartCol(event)}
                  >
                    <MenuItem value="">
                      <em>None</em>
                    </MenuItem>
                    {Items.map((Item) => <MenuItem value={Item}>{Item}</MenuItem> )}
                  </Select>
                  <FormHelperText>Required</FormHelperText>
              </FormControl>

              <FormControl required className={classes.formControl}>
                  <InputLabel id="col-end-index">End index</InputLabel>
                  <Select
                    labelId="col-end-index"
                    id="col-end-index-required"
                    value={this.state.endCol}
                    onChange={(event) => this.handleEndCol(event)}
                  >
                    <MenuItem value="">
                      <em>None</em>
                    </MenuItem>
                    {Items.map((Item) => <MenuItem value={Item}>{Item}</MenuItem> )}
                  </Select>
                  <FormHelperText>Required</FormHelperText>
              </FormControl>

              <TextField
                id="filled-multiline-static"
                label="Column Weights"
                multiline
                style={{width: '100%', paddingBottom: '5%'}}
                rows={7}
                defaultValue=""
                variant="filled"
              />

            <form action="..." method="post" encType="multipart/form-data">

            <input
              type="file"
              name="file"
              ref={(input) => { this.filesInput = input }}
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

        <div className={classes.floatChild} hidden={this.state.initialized === 'false'}>
            <div>
                {
                    this.state.initialized === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Initialized </Typography>
                            <Checkmark size='medium'/>
                        </div>
                        : null
                }

                {
                    this.state.uploaded === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Uploaded </Typography>
                            <Checkmark size='medium'/>
                        </div>
                        : null
                }

                {
                    this.state.generated === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Output file generated </Typography>
                            <Checkmark size='medium'/>
                        </div>
                        : null
                }

                {
                    this.state.solved === true ?
                        <div className={classes.completedTasksContainer}>
                            <Typography className={classes.completedTasks}> Received output </Typography>
                            <Checkmark size='medium'/>
                        </div>
                        : null
                }
            </div>
        </div>
              {
                    this.state.solved === true ?
                        <CsvToHtmlTable
                          data={this.state.csv}
                          tableClassName="table striped hover"
                          csvDelimiter=","
                        />
                        : null
              }
      </div>
    );
  }
}

export default withStyles(styles) (SubmitInput);