import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
} from '@angular/material/dialog';

export interface ApiProblemDetail {
  detail: string;
  instance: string;
  status: number;
  title: string;
}

@Component({
  imports: [MatButton, MatDialogActions, MatDialogClose, MatDialogContent],
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.html',
})
export class ErrorDialog {
  data = inject<ApiProblemDetail>(MAT_DIALOG_DATA);
}
